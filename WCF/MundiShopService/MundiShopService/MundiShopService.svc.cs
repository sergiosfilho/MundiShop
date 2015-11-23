using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using MundiShopService.Contracts.Response;
using System.Configuration;
using System.IO;
using MundiShopService.ContractModels.Request;
using System.Web.Script.Serialization;

namespace MundiShopService
{    
    public class MundiShopService : IMundiShopService
    {
        public ProductResponse[] GetAllProducts()
        {
            using (MundiShopEntities db = new MundiShopEntities())
            {
                List<ProductResponse> retVal = (from Product product in db.Product.OrderBy(p => p.Name)
                    select new ProductResponse()
                    {
                        Id = product.Id,
                        Name = product.Name,
                        Description = product.Description,
                        Price = product.Price
                    }).ToList();
                return retVal.ToArray();
            }
        }

        public string GetProductPhoto(int productId)
        {
            string retVal = null;            
            string imagePath = ConfigurationManager.AppSettings["productImagesPath"] + productId;
            if (Directory.Exists(imagePath))
            {
                string[] files = Directory.GetFiles(imagePath);
                if (files.Length > 0)
                {
                    FileStream fileStream = new FileStream(files[0], FileMode.Open);
                    byte[] bytes = new byte[fileStream.Length];
                    fileStream.Read(bytes, 0, Convert.ToInt32(fileStream.Length));
                    retVal = Convert.ToBase64String(bytes);
                    fileStream.Close();
                    fileStream.Dispose();
                }
            }            
            return retVal;
        }

        /// <summary>
        /// 0=Success, 1=InvalidExpirationDate, 2=InvalidCvv, 3=InvalidProduct, 4=InvalidPaymentAmount
        /// </summary>        
        public int DoPurchase(string purchaseRequest)
        {            
            JavaScriptSerializer serializer = new JavaScriptSerializer();
            PurchaseRequest request = (PurchaseRequest)serializer.Deserialize(purchaseRequest, typeof(PurchaseRequest));
            using (MundiShopEntities db = new MundiShopEntities())
            {
                if (request.CreditCardExpirationYear < DateTime.Now.Year ||
                (request.CreditCardExpirationYear == DateTime.Now.Year && request.CreditCardExpirationMonth < DateTime.Now.Month))
                {                    
                    return 1;
                }
                if (String.IsNullOrWhiteSpace(request.CreditCardCvv) || request.CreditCardCvv.Length != 3)                
                    return 2;                
                Product product = db.Product.FirstOrDefault(p => p.Id == request.ProductId);
                if (product == null)                
                    return 3;
                if (request.AmountPaid != product.Price)
                    return 4;                
                Purchase purchase = new Purchase()
                {
                    CreditCardCvv = request.CreditCardCvv,
                    CreditCardExpirationMonth = request.CreditCardExpirationMonth,
                    CreditCardExpirationYear = request.CreditCardExpirationYear,
                    CreditCardFlag = request.CreditCardFlag,
                    CreditCardNumber = request.CreditCardNumber,
                    CustomerName = request.CustomerName,
                    dateCreate= DateTime.Now
                };
                purchase.Product.Add(product);
                db.Purchase.AddObject(purchase);
                db.SaveChanges();
                return 0;
            }            
        }

        //private void Log(string text)
        //{
        //    string basePath = ConfigurationManager.AppSettings["logErrorPath"];
        //    System.IO.StreamWriter writer = new System.IO.StreamWriter(basePath + "\\log.txt", true);
        //    writer.WriteLine("----------------------------------------------------------");
        //    writer.WriteLine(DateTime.Now.ToString());
        //    writer.WriteLine(text);
        //    writer.WriteLine("----------------------------------------------------------");
        //    writer.Flush();
        //    writer.Close();
        //    writer.Dispose();
        //}

    }
}
