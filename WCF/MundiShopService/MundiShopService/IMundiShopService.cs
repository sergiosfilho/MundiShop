using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using MundiShopService.Contracts.Response;
using MundiShopService.ContractModels.Request;

namespace MundiShopService
{    
    [ServiceContract]
    public interface IMundiShopService
    {
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ProductResponse[] GetAllProducts();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        string GetProductPhoto(int productId);

        //TODO: utilizar o verbo post
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int DoPurchase(string purchaseRequest);
    }
}
