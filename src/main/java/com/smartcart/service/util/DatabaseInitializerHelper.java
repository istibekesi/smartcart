package com.smartcart.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartcart.domain.Category;
import com.smartcart.domain.Edge;
import com.smartcart.domain.Price;
import com.smartcart.domain.Product;
import com.smartcart.domain.Shop;
import com.smartcart.domain.enumeration.UnitEnum;

public class DatabaseInitializerHelper {
	
	private final static Logger log = LoggerFactory.getLogger(DatabaseInitializerHelper.class);

	
	public static List<Category> initCategories() {
		
        List<Category> categories = Arrays.asList( 
        		new Category("MC_FRUIT_AND_VEGETABLES", "Zöldség, gyümölcs")
        			.addSub(new Category("C_VEGETABLES", "Zoldség"))
        			.addSub(new Category("C_FRUIT", "Gyümölcs")
       			), 
        		new Category("MC_MILK_EGG", "Tejtermék")
    				.addSub(new Category("C_MILK", "Tej"))
    				.addSub(new Category("C_EGG", "Tojás")
    			), 
        		new Category("MC_BAKERY", "Pékáru"), 
				new Category("MC_MEAT", "Hús"),
				new Category("MC_FOOD", "Alapvető élémiszer"),
				new Category("MC_DRINKS", "Üdítő")
					.addSub(new Category("C_MINERAL_WATER", "Ásványvíz"))
					.addSub(new Category("C_COFFEE", "Kávé"))
					.addSub(new Category("C_TEE", "Tea"))
					.addSub(new Category("C_JUICE", "Gyümölcslé"))
					.addSub(new Category("C_BEVERAGES", "Üdítőitalok")
				),
				new Category("MC_ALCOHOLIC", "Alkoholos italok")
        			.addSub(new Category("C_BEER", "Sör"))
        			.addSub(new Category("C_CIDER", "Cider"))
        			.addSub(new Category("C_LIQUOR", "Szeszesital"))
        			.addSub(new Category("C_WINE", "Bor"))
        );
        
        return categories; 
        
	}
	
	public static List<Shop> initShops() {
		List<Shop> shops = new ArrayList<Shop>();
		shops.add(new Shop("TESCO"));
		shops.add(new Shop("INTERSPAR"));
		shops.add(new Shop("LIDL"));
		shops.add(new Shop("ALDI"));
		shops.add(new Shop("PENNY"));
		shops.add(new Shop("CBA"));
		return shops;
	}

	public static List<Product> initProducts(List<Category> categories) {
		List<Product> products = new ArrayList<Product>();
		
        products.add(Product.productBuilder("5990000000001", "Beck's világos sör 5%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000002", "Carlsberg minőségi világos sör 5%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000003", "Dreher Classic világos sör 5,2%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000004", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 400, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000005", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 330, categories, "C_BEER"));
        
        products.add(Product.productBuilder("5998818171577", "Soproni Frissen Csapolt világos sör 4,5% 0,5 l", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("0000059905738", "Arany Ászok világos sör 4,3% 0,5 l", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5998817310106", "Arany Ászok világos sör 4,3% 0,5 l", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5998817310397", "Arany Ászok világos sör 4,3% 1,5 l", "", UnitEnum.ml, 1500, categories, "C_BEER"));
        products.add(Product.productBuilder("5998817340196", "Arany Ászok világos sör 4,3% 4 x 0,5", "", UnitEnum.ml, 2000, categories, "C_BEER"));
        
        products.add(Product.productBuilder("5998813122345", "BB Balatonboglári Chardonnay száraz fehérbor 12,5% 0,75 l", "", UnitEnum.ml, 750, categories, "C_WINE"));
        products.add(Product.productBuilder("5999509150338", "Feind Chardonnay száraz fehérbor 12% 750 ml", "", UnitEnum.ml, 750, categories, "C_WINE"));
        products.add(Product.productBuilder("5997508500314", "Figula Balatonszőlősi Sauvignon Blanc száraz fehérbor 12,5% 750 ml", "", UnitEnum.ml, 750, categories, "C_WINE"));
        products.add(Product.productBuilder("5999888023933", "Ikon Chardonnay száraz fehérbor 13% 750 ml", "", UnitEnum.ml, 750, categories, "C_WINE"));
        products.add(Product.productBuilder("5999885392117", "Laposa 4 Hegy Balatoni Olaszrizling száraz fehérbor 12,5% 75 cl", "", UnitEnum.ml, 750, categories, "C_WINE"));
        products.add(Product.productBuilder("5999885392100", "Laposa Illatos Balatoni Zenit - Ottonel muskotály száraz fehérbor 12% 75 cl", "", UnitEnum.ml, 750, categories, "C_WINE"));
        
        products.add(Product.productBuilder("5995099428772", "Zwack Maximilian Tokaji különlegesség likőr 33% 0,5 l", "", UnitEnum.ml, 500, categories, "C_LIQUOR"));
        products.add(Product.productBuilder("4067700011022", "Jägermeister keserűlikőr 35% 0,35 l", "", UnitEnum.ml, 350, categories, "C_LIQUOR"));
        products.add(Product.productBuilder("4067700011015", "Jägermeister keserűlikőr 35% 0,7 l", "", UnitEnum.ml, 700, categories, "C_LIQUOR"));
        products.add(Product.productBuilder("5995099420257", "St. Hubertus 33 gyógynövénylikőr specialitás 33% 0,7 l", "", UnitEnum.ml, 700, categories, "C_LIQUOR"));
        products.add(Product.productBuilder("5995099191720", "Unicum keserű likőr 40% 0,7 l", "", UnitEnum.ml, 700, categories, "C_LIQUOR"));
        
        products.add(Product.productBuilder("8593868003808", "Carling British Cider Apple alma ízesítésű alkoholos ital 4% 0,3 l", "", UnitEnum.ml, 300, categories, "C_CIDER"));
        products.add(Product.productBuilder("8593868003815", "Carling British Cider lime-menta ízesítésű alkoholos ital 4% 0,3 l", "", UnitEnum.ml, 300, categories, "C_CIDER"));
        products.add(Product.productBuilder("8593868003419", "Carling cseresznyés cider 4% 0,3 l", "", UnitEnum.ml, 300, categories, "C_CIDER"));
        products.add(Product.productBuilder("4740098078524", "Fizz áfonyaízű cider 4,7% 0,5 l", "", UnitEnum.ml, 500, categories, "C_CIDER"));
        products.add(Product.productBuilder("4740098079316", "Fizz eperízű cider 4,5% 0,5 l", "", UnitEnum.ml, 300, categories, "C_CIDER"));
        
		return products;
	}

	public static List<Edge> initEdges(List<Product> products) {
		List<Edge> edges = new ArrayList<Edge>();
		
        edges.addAll(Edge.edgeBuilder(products, "5990000000001", "5990000000002", new BigDecimal(0.80) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000002", "5990000000003", new BigDecimal(0.70) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000003", "5990000000004", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000004", "5990000000005", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000001", new BigDecimal(0.75) ));
        
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000003", new BigDecimal(0.40) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000003", new BigDecimal(0.30) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000003", new BigDecimal(0.20) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000003", new BigDecimal(0.10) ));
        
        // init 0.1 within the same category in a "random chain"
        Map<Category, List<Product>> categoryProducts = products
        		.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        
        for (List<Product> productSublist : categoryProducts.values()) {
        	if (productSublist != null && productSublist.size() > 1) {
        		for (int i=0; i < productSublist.size()  ; i++) {
        			Product p1 = productSublist.get(i);
        			
        			int  n = i;
        			Product p2 = productSublist.get(n);

        			while (p1 == p2) {
        				Random rand = new Random();
        				
        				n = rand.nextInt(productSublist.size());
        				
        				//n = i + 1 ;
        				
        				p2 = productSublist.get(n);
        			}
        			
        			edges.addAll(Edge.edgeBuilder(p1, p2, new BigDecimal(0.10) ));
        		}
        	}
        }
        
        return edges;
	}

	public static List<Price> initPrices(List<Product> products, List<Shop> shops) {
		List<Price> prices = new ArrayList<Price>();
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "TESCO", new BigDecimal(199)));
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "INTERSPAR", new BigDecimal(229)));
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "ALDI", new BigDecimal(219)));
        
        prices.add(Price.priceBuilder(products, shops, "5998818171577", "TESCO", new BigDecimal(249)));
        prices.add(Price.priceBuilder(products, shops, "5999509150338", "TESCO", new BigDecimal(189)));
        prices.add(Price.priceBuilder(products, shops, "5998817310106", "TESCO", new BigDecimal(199)));
        prices.add(Price.priceBuilder(products, shops, "5998817310397", "TESCO", new BigDecimal(559)));
        prices.add(Price.priceBuilder(products, shops, "5998817340196", "TESCO", new BigDecimal(799)));
        
        prices.add(Price.priceBuilder(products, shops, "5998813122345", "TESCO", new BigDecimal(799)));
        prices.add(Price.priceBuilder(products, shops, "5999509150338", "TESCO", new BigDecimal(1499)));
        prices.add(Price.priceBuilder(products, shops, "5998813122345", "TESCO", new BigDecimal(2355)));
        prices.add(Price.priceBuilder(products, shops, "5999888023933", "TESCO", new BigDecimal(1390)));
        prices.add(Price.priceBuilder(products, shops, "5999885392117", "TESCO", new BigDecimal(2999)));
        prices.add(Price.priceBuilder(products, shops, "5999885392100", "TESCO", new BigDecimal(1899)));
        		
        prices.add(Price.priceBuilder(products, shops, "5995099428772", "TESCO", new BigDecimal(3149)));
        prices.add(Price.priceBuilder(products, shops, "4067700011022", "TESCO", new BigDecimal(2499)));
        prices.add(Price.priceBuilder(products, shops, "4067700011015", "TESCO", new BigDecimal(4499)));
        prices.add(Price.priceBuilder(products, shops, "5995099420257", "TESCO", new BigDecimal(2599)));
        prices.add(Price.priceBuilder(products, shops, "5995099191720", "TESCO", new BigDecimal(3649)));
        
        prices.add(Price.priceBuilder(products, shops, "8593868003808", "TESCO", new BigDecimal(299)));
        prices.add(Price.priceBuilder(products, shops, "8593868003815", "TESCO", new BigDecimal(299)));
        prices.add(Price.priceBuilder(products, shops, "8593868003419", "TESCO", new BigDecimal(299)));
        prices.add(Price.priceBuilder(products, shops, "4740098078524", "TESCO", new BigDecimal(449)));
        prices.add(Price.priceBuilder(products, shops, "4740098079316", "TESCO", new BigDecimal(449)));
        
        return prices;
	} 
}
