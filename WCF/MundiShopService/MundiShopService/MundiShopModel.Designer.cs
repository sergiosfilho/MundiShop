﻿//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.ComponentModel;
using System.Data.EntityClient;
using System.Data.Objects;
using System.Data.Objects.DataClasses;
using System.Linq;
using System.Runtime.Serialization;
using System.Xml.Serialization;

[assembly: EdmSchemaAttribute()]
#region EDM Relationship Metadata

[assembly: EdmRelationshipAttribute("MundiShopModel", "ProductPurchase", "Product", System.Data.Metadata.Edm.RelationshipMultiplicity.Many, typeof(MundiShopService.Product), "Purchase", System.Data.Metadata.Edm.RelationshipMultiplicity.Many, typeof(MundiShopService.Purchase))]

#endregion

namespace MundiShopService
{
    #region Contexts
    
    /// <summary>
    /// No Metadata Documentation available.
    /// </summary>
    public partial class MundiShopEntities : ObjectContext
    {
        #region Constructors
    
        /// <summary>
        /// Initializes a new MundiShopEntities object using the connection string found in the 'MundiShopEntities' section of the application configuration file.
        /// </summary>
        public MundiShopEntities() : base("name=MundiShopEntities", "MundiShopEntities")
        {
            this.ContextOptions.LazyLoadingEnabled = true;
            OnContextCreated();
        }
    
        /// <summary>
        /// Initialize a new MundiShopEntities object.
        /// </summary>
        public MundiShopEntities(string connectionString) : base(connectionString, "MundiShopEntities")
        {
            this.ContextOptions.LazyLoadingEnabled = true;
            OnContextCreated();
        }
    
        /// <summary>
        /// Initialize a new MundiShopEntities object.
        /// </summary>
        public MundiShopEntities(EntityConnection connection) : base(connection, "MundiShopEntities")
        {
            this.ContextOptions.LazyLoadingEnabled = true;
            OnContextCreated();
        }
    
        #endregion
    
        #region Partial Methods
    
        partial void OnContextCreated();
    
        #endregion
    
        #region ObjectSet Properties
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        public ObjectSet<Product> Product
        {
            get
            {
                if ((_Product == null))
                {
                    _Product = base.CreateObjectSet<Product>("Product");
                }
                return _Product;
            }
        }
        private ObjectSet<Product> _Product;
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        public ObjectSet<Purchase> Purchase
        {
            get
            {
                if ((_Purchase == null))
                {
                    _Purchase = base.CreateObjectSet<Purchase>("Purchase");
                }
                return _Purchase;
            }
        }
        private ObjectSet<Purchase> _Purchase;

        #endregion

        #region AddTo Methods
    
        /// <summary>
        /// Deprecated Method for adding a new object to the Product EntitySet. Consider using the .Add method of the associated ObjectSet&lt;T&gt; property instead.
        /// </summary>
        public void AddToProduct(Product product)
        {
            base.AddObject("Product", product);
        }
    
        /// <summary>
        /// Deprecated Method for adding a new object to the Purchase EntitySet. Consider using the .Add method of the associated ObjectSet&lt;T&gt; property instead.
        /// </summary>
        public void AddToPurchase(Purchase purchase)
        {
            base.AddObject("Purchase", purchase);
        }

        #endregion

    }

    #endregion

    #region Entities
    
    /// <summary>
    /// No Metadata Documentation available.
    /// </summary>
    [EdmEntityTypeAttribute(NamespaceName="MundiShopModel", Name="Product")]
    [Serializable()]
    [DataContractAttribute(IsReference=true)]
    public partial class Product : EntityObject
    {
        #region Factory Method
    
        /// <summary>
        /// Create a new Product object.
        /// </summary>
        /// <param name="id">Initial value of the Id property.</param>
        /// <param name="price">Initial value of the Price property.</param>
        public static Product CreateProduct(global::System.Int32 id, global::System.Double price)
        {
            Product product = new Product();
            product.Id = id;
            product.Price = price;
            return product;
        }

        #endregion

        #region Primitive Properties
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=true, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Int32 Id
        {
            get
            {
                return _Id;
            }
            set
            {
                if (_Id != value)
                {
                    OnIdChanging(value);
                    ReportPropertyChanging("Id");
                    _Id = StructuralObject.SetValidValue(value);
                    ReportPropertyChanged("Id");
                    OnIdChanged();
                }
            }
        }
        private global::System.Int32 _Id;
        partial void OnIdChanging(global::System.Int32 value);
        partial void OnIdChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=true)]
        [DataMemberAttribute()]
        public global::System.String Name
        {
            get
            {
                return _Name;
            }
            set
            {
                OnNameChanging(value);
                ReportPropertyChanging("Name");
                _Name = StructuralObject.SetValidValue(value, true);
                ReportPropertyChanged("Name");
                OnNameChanged();
            }
        }
        private global::System.String _Name;
        partial void OnNameChanging(global::System.String value);
        partial void OnNameChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=true)]
        [DataMemberAttribute()]
        public global::System.String Description
        {
            get
            {
                return _Description;
            }
            set
            {
                OnDescriptionChanging(value);
                ReportPropertyChanging("Description");
                _Description = StructuralObject.SetValidValue(value, true);
                ReportPropertyChanged("Description");
                OnDescriptionChanged();
            }
        }
        private global::System.String _Description;
        partial void OnDescriptionChanging(global::System.String value);
        partial void OnDescriptionChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Double Price
        {
            get
            {
                return _Price;
            }
            set
            {
                OnPriceChanging(value);
                ReportPropertyChanging("Price");
                _Price = StructuralObject.SetValidValue(value);
                ReportPropertyChanged("Price");
                OnPriceChanged();
            }
        }
        private global::System.Double _Price;
        partial void OnPriceChanging(global::System.Double value);
        partial void OnPriceChanged();

        #endregion

    
        #region Navigation Properties
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [XmlIgnoreAttribute()]
        [SoapIgnoreAttribute()]
        [DataMemberAttribute()]
        [EdmRelationshipNavigationPropertyAttribute("MundiShopModel", "ProductPurchase", "Purchase")]
        public EntityCollection<Purchase> Purchase
        {
            get
            {
                return ((IEntityWithRelationships)this).RelationshipManager.GetRelatedCollection<Purchase>("MundiShopModel.ProductPurchase", "Purchase");
            }
            set
            {
                if ((value != null))
                {
                    ((IEntityWithRelationships)this).RelationshipManager.InitializeRelatedCollection<Purchase>("MundiShopModel.ProductPurchase", "Purchase", value);
                }
            }
        }

        #endregion

    }
    
    /// <summary>
    /// No Metadata Documentation available.
    /// </summary>
    [EdmEntityTypeAttribute(NamespaceName="MundiShopModel", Name="Purchase")]
    [Serializable()]
    [DataContractAttribute(IsReference=true)]
    public partial class Purchase : EntityObject
    {
        #region Factory Method
    
        /// <summary>
        /// Create a new Purchase object.
        /// </summary>
        /// <param name="id">Initial value of the Id property.</param>
        /// <param name="creditCardFlag">Initial value of the CreditCardFlag property.</param>
        /// <param name="creditCardExpirationYear">Initial value of the CreditCardExpirationYear property.</param>
        /// <param name="creditCardExpirationMonth">Initial value of the CreditCardExpirationMonth property.</param>
        /// <param name="dateCreate">Initial value of the dateCreate property.</param>
        public static Purchase CreatePurchase(global::System.Int32 id, global::System.Int32 creditCardFlag, global::System.Int32 creditCardExpirationYear, global::System.Int32 creditCardExpirationMonth, global::System.DateTime dateCreate)
        {
            Purchase purchase = new Purchase();
            purchase.Id = id;
            purchase.CreditCardFlag = creditCardFlag;
            purchase.CreditCardExpirationYear = creditCardExpirationYear;
            purchase.CreditCardExpirationMonth = creditCardExpirationMonth;
            purchase.dateCreate = dateCreate;
            return purchase;
        }

        #endregion

        #region Primitive Properties
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=true, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Int32 Id
        {
            get
            {
                return _Id;
            }
            set
            {
                if (_Id != value)
                {
                    OnIdChanging(value);
                    ReportPropertyChanging("Id");
                    _Id = StructuralObject.SetValidValue(value);
                    ReportPropertyChanged("Id");
                    OnIdChanged();
                }
            }
        }
        private global::System.Int32 _Id;
        partial void OnIdChanging(global::System.Int32 value);
        partial void OnIdChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=true)]
        [DataMemberAttribute()]
        public global::System.String CustomerName
        {
            get
            {
                return _CustomerName;
            }
            set
            {
                OnCustomerNameChanging(value);
                ReportPropertyChanging("CustomerName");
                _CustomerName = StructuralObject.SetValidValue(value, true);
                ReportPropertyChanged("CustomerName");
                OnCustomerNameChanged();
            }
        }
        private global::System.String _CustomerName;
        partial void OnCustomerNameChanging(global::System.String value);
        partial void OnCustomerNameChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=true)]
        [DataMemberAttribute()]
        public global::System.String CreditCardNumber
        {
            get
            {
                return _CreditCardNumber;
            }
            set
            {
                OnCreditCardNumberChanging(value);
                ReportPropertyChanging("CreditCardNumber");
                _CreditCardNumber = StructuralObject.SetValidValue(value, true);
                ReportPropertyChanged("CreditCardNumber");
                OnCreditCardNumberChanged();
            }
        }
        private global::System.String _CreditCardNumber;
        partial void OnCreditCardNumberChanging(global::System.String value);
        partial void OnCreditCardNumberChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=true)]
        [DataMemberAttribute()]
        public global::System.String CreditCardCvv
        {
            get
            {
                return _CreditCardCvv;
            }
            set
            {
                OnCreditCardCvvChanging(value);
                ReportPropertyChanging("CreditCardCvv");
                _CreditCardCvv = StructuralObject.SetValidValue(value, true);
                ReportPropertyChanged("CreditCardCvv");
                OnCreditCardCvvChanged();
            }
        }
        private global::System.String _CreditCardCvv;
        partial void OnCreditCardCvvChanging(global::System.String value);
        partial void OnCreditCardCvvChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Int32 CreditCardFlag
        {
            get
            {
                return _CreditCardFlag;
            }
            set
            {
                OnCreditCardFlagChanging(value);
                ReportPropertyChanging("CreditCardFlag");
                _CreditCardFlag = StructuralObject.SetValidValue(value);
                ReportPropertyChanged("CreditCardFlag");
                OnCreditCardFlagChanged();
            }
        }
        private global::System.Int32 _CreditCardFlag;
        partial void OnCreditCardFlagChanging(global::System.Int32 value);
        partial void OnCreditCardFlagChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Int32 CreditCardExpirationYear
        {
            get
            {
                return _CreditCardExpirationYear;
            }
            set
            {
                OnCreditCardExpirationYearChanging(value);
                ReportPropertyChanging("CreditCardExpirationYear");
                _CreditCardExpirationYear = StructuralObject.SetValidValue(value);
                ReportPropertyChanged("CreditCardExpirationYear");
                OnCreditCardExpirationYearChanged();
            }
        }
        private global::System.Int32 _CreditCardExpirationYear;
        partial void OnCreditCardExpirationYearChanging(global::System.Int32 value);
        partial void OnCreditCardExpirationYearChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.Int32 CreditCardExpirationMonth
        {
            get
            {
                return _CreditCardExpirationMonth;
            }
            set
            {
                OnCreditCardExpirationMonthChanging(value);
                ReportPropertyChanging("CreditCardExpirationMonth");
                _CreditCardExpirationMonth = StructuralObject.SetValidValue(value);
                ReportPropertyChanged("CreditCardExpirationMonth");
                OnCreditCardExpirationMonthChanged();
            }
        }
        private global::System.Int32 _CreditCardExpirationMonth;
        partial void OnCreditCardExpirationMonthChanging(global::System.Int32 value);
        partial void OnCreditCardExpirationMonthChanged();
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [EdmScalarPropertyAttribute(EntityKeyProperty=false, IsNullable=false)]
        [DataMemberAttribute()]
        public global::System.DateTime dateCreate
        {
            get
            {
                return _dateCreate;
            }
            set
            {
                OndateCreateChanging(value);
                ReportPropertyChanging("dateCreate");
                _dateCreate = StructuralObject.SetValidValue(value);
                ReportPropertyChanged("dateCreate");
                OndateCreateChanged();
            }
        }
        private global::System.DateTime _dateCreate;
        partial void OndateCreateChanging(global::System.DateTime value);
        partial void OndateCreateChanged();

        #endregion

    
        #region Navigation Properties
    
        /// <summary>
        /// No Metadata Documentation available.
        /// </summary>
        [XmlIgnoreAttribute()]
        [SoapIgnoreAttribute()]
        [DataMemberAttribute()]
        [EdmRelationshipNavigationPropertyAttribute("MundiShopModel", "ProductPurchase", "Product")]
        public EntityCollection<Product> Product
        {
            get
            {
                return ((IEntityWithRelationships)this).RelationshipManager.GetRelatedCollection<Product>("MundiShopModel.ProductPurchase", "Product");
            }
            set
            {
                if ((value != null))
                {
                    ((IEntityWithRelationships)this).RelationshipManager.InitializeRelatedCollection<Product>("MundiShopModel.ProductPurchase", "Product", value);
                }
            }
        }

        #endregion

    }

    #endregion

    
}
