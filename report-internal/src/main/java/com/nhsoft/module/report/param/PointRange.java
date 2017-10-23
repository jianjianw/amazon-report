package com.nhsoft.module.report.param;

import org.dom4j.Element;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class PointRange implements Serializable {

	private static final long serialVersionUID = 883388067817652588L;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal prop;

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getProp() {
		return prop;
	}

	public void setProp(BigDecimal prop) {
		this.prop = prop;
	}

	public static List<PointRange> readFromNode(Element node){
		List<PointRange> pointRanges = new ArrayList<PointRange>();
		Iterator<Element> iterator = node.elementIterator("POINT_RANGE");
		while(iterator.hasNext()){
			Element element = iterator.next();
			PointRange pointRange = new PointRange();
			
			Element subElement = (Element) element.selectSingleNode("Low");
			if(subElement != null){
				pointRange.setLow(new BigDecimal(subElement.getText()));
			} else {
				pointRange.setLow(BigDecimal.ZERO);
			}
			
			subElement = (Element) element.selectSingleNode("High");
			if(subElement != null){
				pointRange.setHigh(new BigDecimal(subElement.getText()));
			} else {
				pointRange.setHigh(BigDecimal.ZERO);
			}
			
			subElement = (Element) element.selectSingleNode("Prop");
			if(subElement != null){
				pointRange.setProp(new BigDecimal(subElement.getText()));
			} else {
				pointRange.setProp(BigDecimal.ZERO);
			}
			pointRanges.add(pointRange);
		}	
		return pointRanges;
	}
	
}
