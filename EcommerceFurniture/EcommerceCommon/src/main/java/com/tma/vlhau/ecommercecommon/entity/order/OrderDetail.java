package com.tma.vlhau.ecommercecommon.entity.order;

import com.tma.vlhau.ecommercecommon.entity.Category;
import com.tma.vlhau.ecommercecommon.entity.IdBasedEntity;
import com.tma.vlhau.ecommercecommon.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail extends IdBasedEntity {
	
	  
	    private int quantity;
	    private float productCost;
	    private float shippingCost;
	    private float unitPrice;
	    private float subtotal;
	  
		@ManyToOne
		@JoinColumn(name = "product_id")
		private Product product;
		
		@ManyToOne
		@JoinColumn(name = "order_id")
		private Order order;

		public OrderDetail() {

		}

	public OrderDetail(String categoryName, int quantity, float productCost, float shippingCost, float subtotal, Product product) {
		this.product = product;
		this.product.setCategory(new Category(categoryName));
		this.quantity = quantity;
		this.productCost = productCost;
		this.shippingCost = shippingCost;
		this.subtotal = subtotal;
	}

	public OrderDetail(int quantity, String productName, float productCost, float shippingCost, float subtotal) {
		this.product = new Product(productName);
		this.quantity = quantity;
		this.productCost = productCost;
		this.shippingCost = shippingCost;
		this.subtotal = subtotal;
	}

}
