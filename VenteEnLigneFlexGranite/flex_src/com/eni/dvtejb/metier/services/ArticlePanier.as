/**
 * Generated by Gas3 v2.1.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package com.eni.dvtejb.metier.services {
	
	 import com.eni.dvtejb.metier.entities.Article;

    [Bindable]
    [RemoteClass(alias="com.eni.dvtejb.metier.services.ArticlePanier")]
    public class ArticlePanier {
    	public var article:Article;
        public var quantite:Number;
    }
}