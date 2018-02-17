package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	final static int PRICE_OPTIONS_NUMBER = 3;
	final static int NUMBER_OF_STYLES_PER_PAGE = 36;

	// user parameters
	String search;
	boolean isMen;
	int minPrice; // 1 2 3
	int maxPrice; // 1 2 3

	// asos parameters
	int totalPages;
	int totalStyles;
	int lowestPrice;
	int highestPrice;


	//private ConcurrentHashMap<Integer, AsosItem> hm;


	public Crawler(String search) throws IOException {
		this(search,false,1,PRICE_OPTIONS_NUMBER);
	}

	Elements elementsProducts;
	public Crawler(String search,boolean isMen,int minPrice,int maxPrice) throws IOException {

		this.search = search + "";
		this.isMen = isMen;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;


		// http://us.asos.com/search/?q=black&pge=2&refine=floor:1000|currentprice:65<770&currentpricerange=0-770&pgesize=36
		String url = createInitialUrl();
		System.out.println("URL >> " + url);

		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").timeout(10*1000).get();


		Elements elementsTotalStyles = doc.getElementsByClass("total-results");
		String totalStyles = elementsTotalStyles.get(0).text();
		this.totalStyles = Integer.parseInt(totalStyles.replaceAll(",", ""));
		this.totalPages = this.totalStyles / NUMBER_OF_STYLES_PER_PAGE;

		System.out.println("totalStyles >> " + this.totalStyles);
		System.out.println("totalPages >> " + this.totalPages);
		
		elementsProducts = doc.getElementsByClass("product-container");
		System.out.println("ELEMENT PRODUCTS LEN >> " + elementsProducts.size());
		System.out.println();
		System.out.println();


		String vendor = "ASOS";
		String vendorLogo = "yoyo";//doc.getElementById("logo").select("img").first().absUrl("src");
//		System.out.println("VENDOR LOGO >> " + vendorLogo);

		//		hm = new ConcurrentHashMap<Integer,AsosItem>(this.totalStyles*2);
		al = new ArrayList<AsosItem>(this.totalStyles);


		for(int i=0;i<elementsProducts.size();i++){
			Runnable r = new MyThread(i, vendor, vendorLogo);
			new Thread(r).start();
		}

	}

	public class MyThread implements Runnable {

		int e;
		String vendor;
		String vendorLogo;

		public MyThread(int num,String vendor,String vendorLogo) {
			// store parameter for later user
			this.e = num;
			this.vendor = vendor + "";
			this.vendorLogo = vendorLogo + "";
		}

		public void run() {
			//System.out.println("thread number >> " + e);
			addElementToHM(elementsProducts.get(e),vendor,vendorLogo);
		}
	}

	private String createInitialUrl(){

		return createUrl(0);

	}

	private String createUrl(int pageNumber){

		String url = "http://us.asos.com/search/?q=";
		url = url + search;

		if(pageNumber>0)
			url = url + "&pge=" + pageNumber;

		url = url + "&refine=floor:" + (isMen?"1001":"1000");
		
//		url = url + "|currentprice:"+ 770/3*this.minPrice +"<"+ 770/3*this.maxPrice+1 +"&currentpricerange=0-770";
		url = url + "|currentprice:0<770&currentpricerange=0-770";
		url = url + "&pgesize=" + NUMBER_OF_STYLES_PER_PAGE;
		url = url + "&sort=freshness";

		return url;
	}

	private void addElementToHM(Element e,String vendor,String vendorLogo){

		try {
			String index = e.attr("data-productid");
			//			System.out.println("index >> " + index);

			String name = e.getElementsByClass("name").first().text();
			//			System.out.println("name >> " + name);

			String price = e.getElementsByClass("price-current").first().getElementsByClass("price").first().text();
			//			System.out.println("price >> " + price);

			String link = e.getElementsByClass("product-link").first().attr("href");
			//			System.out.println("LINK >> " + link);

			//			System.out.println();

			//			Document doc = Jsoup.connect(link).timeout(10*1000).get();
			//http://images.asos-media.com/products/asos-salon-extreme-lace-bonded-black-midi-prom-dress/6867226-1-black?$XXL$&wid=513&fit=constrain

			String shipDate = (new Random().nextInt(27)+1) + "/12";
			//			System.out.println(shipDate);

			String[] images = getImagesFromProductLink(link);
			// http://images.asos-media.com/products/asos-salon-extreme-lace-bonded-black-midi-prom-dress/6867226-1-black?$xxl$&fit=constrain


			String[] tags = {"tag1","tag2","tag3"};


			//hm.put(Integer.parseInt(index), new AsosItem(index, name, price, shipDate, link, images, tags, vendor, vendorLogo));
			synchronized (al) {
				al.add(new AsosItem(index, name, price, shipDate, link, images, tags, vendor, vendorLogo));
			}

			//			System.out.println();

		}catch (Exception ex) {
			// TODO: handle exception
		}
	}

	private String[] getImagesFromProductLink(String link) throws IOException{

		String[] images = new String[4];


		Document doc = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").timeout(10*1000).get();

		Elements e = doc.getElementsByClass("thumbnails").first().select("img");

		for(int i=0;i<images.length;i++) {
			images[i] = e.get(i).attr("src").replace("?$S$&wid=40", "?$xxl$&fit=constrain");
			//			System.out.println(images[i]);
		}

		return images;
	}

	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////


	//	private int indexOfMap = 0;
	private int indexOfArray = 0;
	private ArrayList<AsosItem> al;

	public ArrayList<AsosItem> getNextAsosItem(int num){

//		System.out.println("actual size of array >> " + this.al.size());
//		System.out.println("size of array >> " + indexOfArray);
//		System.out.println("num >> " + num);
		
		ArrayList<AsosItem> temp = new ArrayList<AsosItem>(num);

		try{
			for(int i=0;i<num;i++){
				temp.add(al.get(indexOfArray++));
			}
		} catch(IndexOutOfBoundsException ex) {
			System.out.println("ERROR outOfBounds >> " + indexOfArray);
		}

		//System.out.println("size of array >> " + indexOfArray);

		//System.out.println("temp size >> " + temp.size());

		return temp;
	}

	private int stylesInPool = -1;
	private int timesRequest = 0;

	public int stylesLeft(){

		this.timesRequest++;

		if(this.timesRequest == this.al.size())
			return 1;


		if(stylesInPool == -1) {
			this.stylesInPool = this.totalStyles;
			return this.stylesInPool;
		}

		if(this.stylesInPool == 1)
			return 1;


		if(this.stylesInPool > 100){
			this.stylesInPool = this.stylesInPool - this.stylesInPool*(new Random().nextInt(40)+10)/100;
			return this.stylesInPool;
		}

		if(this.stylesInPool > 10) {
			this.stylesInPool = this.stylesInPool - this.stylesInPool*(new Random().nextInt(10)+10)/100;
			return this.stylesInPool;
		}

		this.stylesInPool -= 1;
		return this.stylesInPool;
	}
}

