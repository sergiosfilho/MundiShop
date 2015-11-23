using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace MundiShopService.ContractModels.Request
{
    [DataContract]
    public class PurchaseRequest
    {
        [DataMember]
        public int ProductId { get; set; }
        [DataMember]
        public string CustomerName { get; set; }
        [DataMember]
        public string CreditCardNumber { get; set; }
        [DataMember]
        public int CreditCardExpirationYear { get; set; }
        [DataMember]
        public int CreditCardExpirationMonth { get; set; }
        [DataMember]
        public string CreditCardCvv { get; set; }
        [DataMember]
        public int CreditCardFlag { get; set; }
        [DataMember]
        public double AmountPaid { get; set; }
    }
}