package crawler;


import java.io.Serializable;
import java.util.Arrays;

public class AsosItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	String index;
	String name;
	String price;
	String shipDate;
	
	String link;
	String[] images;
	String[] tags;
	
	String vendor;
	String vendorLogo;	
	
	///////////////////////////////////////////////////
	/////////////////// FOR SERVER ////////////////////
	///////////////////////////////////////////////////
	
	/*
	 *  EXAMPLE(non-Javadoc)
	 *  {"35": {"images": ["http://images.asos-media.com/products/asos-curve-mini-skater-dress-in-scribble-floral-print/6732815-1-multi", "http://images.asos-media.com/products/asos-curve-mini-skater-dress-in-scribble-floral-print/6732815-2", "http://images.asos-media.com/products/asos-curve-mini-skater-dress-in-scribble-floral-print/6732815-3", "http://images.asos-media.com/products/asos-curve-mini-skater-dress-in-scribble-floral-print/6732815-4"], "index": "35", "link": "http://www.asos.com/asos-curve/asos-curve-mini-skater-dress-in-scribble-floral-print/prod/pgeproduct.aspx?iid=6732815&clr=Multi&SearchQuery=summer+dress", "name": "ASOS CURVE Mini Skater Dress in Scribble Floral Print", "price": null, "shipDate": "23/10", "tags": ["Woven fabric", "V-neckline", "Flattering high-rise stretch waistband", "Tie back detail", "All-over floral print", "Regular fit - true to size", "Machine wash", "100% Viscose", "Our model wears a UK 18/EU 46/US 14 and is 175cm/5'9\" tall"], "vendor": "asos", "vendorLogo": "http://content.asos-media.com/~/media/240613124858en-GB/Images/uk/Archive/june/asos-logo.png"}}
	 *  
	 */
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("index >> " + index + "\n");
		sb.append("name >> " + name + "\n");
		sb.append("price >> " + price + "\n");
		sb.append("shipDate >> " + shipDate + "\n");
		
		sb.append("link >> " + link + "\n");
		
		sb.append("images >> ");
		for(int i=0;i<images.length;i++)
			sb.append(images[i] + " | ");
		sb.append("\n");
		
		sb.append("tags >> ");
		for(int i=0;i<tags.length;i++)
			sb.append(tags[i] + " | ");
		sb.append("\n");
		
		sb.append("vendor >> " + vendor + "\n");
		sb.append("vendorLogo >> " + vendorLogo + "\n");
		
		return sb.toString();
	}
	
	protected AsosItem() {
		// TODO Auto-generated constructor stub
	}
	
	protected AsosItem(String source) {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public AsosItem(String index, String name, String price, String shipDate, String link, String[] images,
			String[] tags, String vendor, String vendorLogo) {
		super();
		this.index = index;
		this.name = name;
		this.price = price;
		this.shipDate = shipDate;
		this.link = link;
		this.images = images;
		this.tags = tags;
		this.vendor = vendor;
		this.vendorLogo = vendorLogo;
	}

	protected String getIndex() {
		return index;
	}

	protected void setIndex(String index) {
		this.index = index;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getPrice() {
		return price;
	}

	protected void setPrice(String price) {
		this.price = price;
	}

	protected String getShipDate() {
		return shipDate;
	}

	protected void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	protected String getLink() {
		return link;
	}

	protected void setLink(String link) {
		this.link = link;
	}

	protected String[] getImages() {
		return images;
	}

	protected void setImages(String[] images) {
		
		if(images==null)
			this.images = null;
		else
			this.images = Arrays.copyOf(images, images.length);

	}
	
	protected String[] getTags() {
		return tags;
	}

	protected void setTags(String[] tags) {
		
		if(tags==null)
			this.tags = null;
		else
			this.tags = Arrays.copyOf(tags, tags.length);
	}

	protected String getVendor() {
		return vendor;
	}

	protected void setVendor(String vendor) {
		this.vendor = vendor;
	}

	protected String getVendorLogo() {
		return vendorLogo;
	}

	protected void setVendorLogo(String vendorLogo) {
		this.vendorLogo = vendorLogo;
	}

	
	
	///////////////////////////////////////////////////
	///////////////////// FOR UI //////////////////////
	///////////////////////////////////////////////////
	
	public String UI_getItemURL(){
		// TODO Auto-generated method stub
		return this.link; 
	}
	
	public String UI_getMainImageURL() {
		// TODO Auto-generated method stub
		return this.images[0];
	}

	public String[] UI_getImagesURLs() {
		// TODO Auto-generated method stub
		return this.images;
	}

	public String UI_getCompanyLogoURL() {
		// TODO Auto-generated method stub
		return this.vendorLogo;
	}

	public String UI_getDescription() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String UI_getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}

	public String UI_getShippingDate() {
		// TODO Auto-generated method stub
		return this.shipDate;
	}
	

	
}
