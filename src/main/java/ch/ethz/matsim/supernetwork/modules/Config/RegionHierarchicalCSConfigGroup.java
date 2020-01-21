/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules.Config;

import org.matsim.core.config.ReflectiveConfigGroup;
import org.matsim.core.config.ReflectiveConfigGroup.StringGetter;
import org.matsim.core.config.ReflectiveConfigGroup.StringSetter;

/**
 * @author stefanopenazzi
 *
 */
public class RegionHierarchicalCSConfigGroup extends ReflectiveConfigGroup {

	

	public static final String GROUP_NAME = "RegionHierarchicalCS";
	
	public static final String CUT = "cut";
	
	public static final String LINKAGE = "linkage";
	
	private int cut = 1000;
	
	private String linkage = "ward";
	
	public RegionHierarchicalCSConfigGroup() {
		super(GROUP_NAME);
		// TODO Auto-generated constructor stub
	}
	
	@StringGetter(CUT)
	public int getCut() {
		return cut;
	}

	@StringSetter(CUT)
	public void setCut(int cut) {
		this.cut =cut;
	}
	
	@StringGetter(LINKAGE)
	public String getLinkage() {
		return linkage;
	}

	@StringSetter(LINKAGE)
	public void setLinkage(String likage) {
		this.linkage = linkage;
	}

}
