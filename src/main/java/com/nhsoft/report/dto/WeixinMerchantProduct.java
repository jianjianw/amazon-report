package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class WeixinMerchantProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5519316959600415633L;
	public String productId;
	 /* 必填 商品名称
	 */
    public String name;

    /**
	 * 必填 商品分类
	 */
    public List<String> categoryIdList;

    /**
	 * 必填 商品主图
	 */
    public String mainImg;

    /**
     * 必填 商品图片列表
     */
    public List<String> imgList; //缩略图

    /**
     * 必填 商品详情列表
     */
    public List<ProductDetail> productDetails;

    /**
     * 商品属性
     */
    public List<ProductProperty> productProperties;

    /**
     * 商品SKU
     */
    public List<ProductSkuInfo> productSkuInfos;

    /**
     * 限购数量
     */
    public Integer buyLimit;
    
    /**
     * 商品SKU信息
     */
    public List<ProductSku> productSkus;
    
    /**
     * 商品其他属性
     */
    public ProductExtAttr productExtAttr;
    
    /**
 	 * 必填 运费信息
 	 */
    public ProductDeliverInfo productDeliverInfo;

    /** 商品状态(0-全部, 1-上架, 2-下架) */
    public Integer status;
    
    public List<ShopPosItemImage> shopPosItemImages = new ArrayList<ShopPosItemImage>();


    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<String> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	public List<ProductProperty> getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(List<ProductProperty> productProperties) {
		this.productProperties = productProperties;
	}

	public List<ProductSkuInfo> getProductSkuInfos() {
		return productSkuInfos;
	}

	public void setProductSkuInfos(List<ProductSkuInfo> productSkuInfos) {
		this.productSkuInfos = productSkuInfos;
	}

	public Integer getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(Integer buyLimit) {
		this.buyLimit = buyLimit;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}

	public ProductExtAttr getProductExtAttr() {
		return productExtAttr;
	}

	public void setProductExtAttr(ProductExtAttr productExtAttr) {
		this.productExtAttr = productExtAttr;
	}

	public ProductDeliverInfo getProductDeliverInfo() {
		return productDeliverInfo;
	}

	public void setProductDeliverInfo(ProductDeliverInfo productDeliverInfo) {
		this.productDeliverInfo = productDeliverInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ShopPosItemImage> getShopPosItemImages() {
		return shopPosItemImages;
	}

	public void setShopPosItemImages(List<ShopPosItemImage> shopPosItemImages) {
		this.shopPosItemImages = shopPosItemImages;
	}

	/**
     * 商品详情列表，显示在客户端的商品详情页内
     */
    public static class  ProductDetail implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = -5166850156075571030L;

        /**
         * 商品描述
         */
		public String text; 

		/**
		 * 商品详情图片
		 */
        public String img; 
        
        public ProductDetail(){
        	
        }
        
        public ProductDetail(String text, String img){
        	this.text = text;
        	this.img = img;
        }

        @Override
        public String toString() {
            return "Detail [text=" + text + ", img=" + img + "]";
        }

    }
    
    /**
     * 商品属性列表，属性列表请通过《获取指定分类的所有属性》获取
     */
    public static class ProductProperty implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = 2476640851607469491L;

		/** 属性 id */
        public String id;

        /** 属性值 id */
        public String vid;
        
        public ProductProperty(){
        	
        }
        
        public ProductProperty(String id, String vid){
        	this.id = id;
        	this.vid = vid;
        }

        @Override
        public String toString() {
            return "Property [id=" + id + ", vid=" + vid + "]";
        }

    }
    
    /**
     * 商品 sku 定义，SKU 列表请通过《获取指定子分类的所有 SKU》获取
     */
    public static class  ProductSkuInfo implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = -7775676985979738895L;

		/** sku 属性(SKU 列表中 id, 支持自定义 SKU，格式为 "$xxx"，xxx 即为显示在客户端中的字符串) */
        public String id;

        /** sku 值(SKU 列表中 vid, 如需自定义 SKU，格式为 "$xxx"，xxx 即为显示在客户端中的字符串) */
        public List<String> vidList;

        @Override
        public String toString() {
            return "SkuInfo [id=" + id + ", vidList=" + vidList + "]";
        }

    }
    
    /**
     * sku 信息列表(可为多个)，每个 sku 信息串即为一个确定的商品，比如白色的37码的鞋子
     */
    public static class ProductSku implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = -375865809929746263L;

		/**
         * sku 信息, 参照上述 sku_table 的定义; <br>
         * 格式 : "id1:vid1;id2:vid2" <br>
         * 规则 : id_info 的组合个数必须与 sku_table 个数一致<br>
         * (若商品无 sku 信息, 即商品为统一规格，则此处赋值为空字符串即可)<br>
         */
        public String skuId;

        /** sku 原价(单位 : 分) */
        public Integer oriPrice;

        /** sku 微信价(单位 : 分, 微信价必须比原价小, 否则添加商品失败) */
        public Integer price;

        /** sku iconurl(图片需调用图片上传接口获得图片 Url) */
        public String iconUrl;

        /** sku 库存 */
        public Integer quantity;

        /** 商家商品编码 */
        public String productCode;

        @Override
        public String toString() {
            return "Sku [skuId=" + skuId + ", oriPrice=" + oriPrice + ", price=" + price + ", iconUrl=" + iconUrl + ", quantity=" + quantity + ", productCode=" + productCode + "]";
        }

    }
    
    /**
     * 商品其他属性
     */
    public static class  ProductExtAttr implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = -8952462909836633857L;

		/** 是否包邮(0-否, 1-是), 如果包邮 delivery_info 字段可省略 */
        public Integer isPostFree;

        /** 是否提供发票(0-否, 1-是) */
        public Integer isHasReceipt;

        /** 是否保修(0-否, 1-是) */
        public Integer isUnderGuaranty;

        /** 是否支持退换货(0-否, 1-是) */
        public Integer isSupportReplace;

        /** 国家(详见《地区列表》说明) */
        public String country;

        /** 省份(详见《地区列表》说明) */
        public String province;

        /** 城市(详见《地区列表》说明) */
        public String city;

        /** 地址 */
        public String address;

    }
    
    /**
     * 运费信息
     */
    public static class  ProductDeliverInfo implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = 7061452027732892939L;

		/**
         * 运费类型(0-使用下面 express 字段的默认模板, 1-使用 template_id 代表的邮费模板, 详见邮费模板相关 API)
         */
        public Integer deliveryType;

        /** 邮费模板 ID */
        public Integer templateId;

        /** 快递列表 */
        public List<WeixinExpress> expressList;

        @Override
        public String toString() {
            return "DeliverInfo [deliveryType=" + deliveryType + ", templateId=" + templateId + ", expressList=" + expressList + "]";
        }

        /**
         * 快递
         */
        public static class  WeixinExpress implements Serializable {


            /**
			 * 
			 */
            private static final long serialVersionUID = -644986125838960883L;

			/** 快递 ID（参考ExpressType） */
            public Integer id;

            /** 运费(单位 : 分) */
            public Integer price;

            @Override
            public String toString() {
                return "Express [id=" + id + ", price=" + price + "]";
            }

        }

    }
}
