package com.nhsoft.module.report.comparator;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparatorGroupModelData<T> implements Comparator<T> {
	private static final Logger LOG = LoggerFactory.getLogger(ComparatorGroupModelData.class);

	private String groupField;
	private String sortField;
	private String sortType;
	private Method method = null;
	private Method groupMethod = null;
	private boolean sortPinyin = true;

	public boolean isSortPinyin() {
		return sortPinyin;
	}

	public void setSortPinyin(boolean sortPinyin) {
		this.sortPinyin = sortPinyin;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public ComparatorGroupModelData(String groupField, String sortField, String sortType, Class<T> clazz){
		this.groupField = groupField;
		this.sortField = sortField;
		this.sortType = sortType;
		
		try {
			String gettter = AppUtil.toGetter(sortField);
			if ( gettter != null) {
				method = clazz.getMethod(gettter);
			}
			String gettter1 = AppUtil.toGetter(groupField);
			if ( gettter1 != null) {
				groupMethod = clazz.getMethod(gettter1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	@Override
	public int compare(T o1, T o2) {
		if (groupMethod == null) {
			return 0;
		}
		if (method == null) {
			return 0;
		}
		if (StringUtils.isEmpty(groupField) || StringUtils.isEmpty(sortType)) {
			return 0;
		}
		if (StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortType)) {
			return 0;
		}

		Object val1 = null;
		Object val2 = null;
		Object grp1 = null;
		Object grp2 = null;
		try {
			val1 = method.invoke(o1);
			val2 = method.invoke(o2);
			grp1 = groupMethod.invoke(o1);
			grp2 = groupMethod.invoke(o2);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		if(sortType != null && sortType.equals("NONE")){
			if (grp1 == null && grp2 != null) {
				return -1;
			} else if (grp1 != null && grp2 == null) {
				return 1;
			} else if (grp1 == null && grp2 == null) {
				return 0;
			}
			if (grp1 instanceof Integer) {
				if ((Integer) grp1 > (Integer) grp2) {
					return 1;
				} else if ((Integer) grp1 < (Integer) grp2) {
					return -1;
				} else {
					return 0;
				}
			} else if (grp1 instanceof String) {
				String gstr1 = null;
				String gstr2 = null;
				if(sortPinyin){
					gstr1 = AppUtil.cn2py((String) grp1);
					gstr2 = AppUtil.cn2py((String) grp2);
				}else{
					gstr1 = (String) grp1;
					gstr2 = (String) grp2;
				}
				if ((gstr1).compareTo(gstr2) > 0) {
					return 1;
				} else if ((gstr1).compareTo(gstr2) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		if (sortType != null && sortType.equals("ASC")) {
			if (grp1 == null && grp2 != null) {
				return -1;
			} else if (grp1 != null && grp2 == null) {
				return 1;
			} else if (grp1 == null && grp2 == null) {
				return 0;
			}
			if (grp1 instanceof Integer) {
				if ((Integer) grp1 > (Integer) grp2) {
					return 1;
				} else if ((Integer) grp1 < (Integer) grp2) {
					return -1;
				} else {
					if (val1 == null && val2 != null) {
						return -1;
					} else if (val1 != null && val2 == null) {
						return 1;
					} else if (val1 == null && val2 == null) {
						return 0;
					}
					if (val1 instanceof Integer) {
						if ((Integer) val1 > (Integer) val2) {
							return 1;
						} else if ((Integer) val1 < (Integer) val2) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Float) {

						if ((Float) val1 > (Float) val2) {
							return 1;
						} else if ((Float) val1 < (Float) val2) {
							return -1;
						} else {
							return 0;
						}

					} else if (val1 instanceof Double) {

						if ((Double) val1 > (Double) val2) {
							return 1;
						} else if ((Double) val1 < (Double) val2) {
							return -1;
						} else {
							return 0;
						}

					} else if (val1 instanceof BigDecimal) {
						if (((BigDecimal) val1).compareTo((BigDecimal) val2) > 0) {
							return 1;
						} else if (((BigDecimal) val1).compareTo((BigDecimal) val2) < 0) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Date) {
						if (((Date) val1).after((Date) val2)) {
							return 1;
						} else if (((Date) val1).before((Date) val2)) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof String) {
						String str1 = AppUtil.cn2py((String) val1);
						String str2 = AppUtil.cn2py((String) val2);
						if ((str1).compareTo(str2) > 0) {
							return 1;
						} else if ((str1).compareTo(str2) < 0) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Boolean) {
						if (((Boolean) val1).compareTo((Boolean) val2) > 0) {
							return 1;
						} else if (((Boolean) val1).compareTo((Boolean) val2) < 0) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			} else if (grp1 instanceof String) {
				String gstr1 = null;
				String gstr2 = null;
				if(sortPinyin){
					gstr1 = AppUtil.cn2py((String) grp1);
					gstr2 = AppUtil.cn2py((String) grp2);
				}else{
					gstr1 = (String) grp1;
					gstr2 = (String) grp2;
				}
				if ((gstr1).compareTo(gstr2) > 0) {
					return 1;
				} else if ((gstr1).compareTo(gstr2) < 0) {
					return -1;
				} else {
					if (val1 == null && val2 != null) {
						return -1;
					} else if (val1 != null && val2 == null) {
						return 1;
					} else if (val1 == null && val2 == null) {
						return 0;
					}
					if (val1 instanceof Integer) {
						if ((Integer) val1 > (Integer) val2) {
							return 1;
						} else if ((Integer) val1 < (Integer) val2) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Float) {

						if ((Float) val1 > (Float) val2) {
							return 1;
						} else if ((Float) val1 < (Float) val2) {
							return -1;
						} else {
							return 0;
						}

					} else if (val1 instanceof Double) {

						if ((Double) val1 > (Double) val2) {
							return 1;
						} else if ((Double) val1 < (Double) val2) {
							return -1;
						} else {
							return 0;
						}

					} else if (val1 instanceof BigDecimal) {
						if (((BigDecimal) val1).compareTo((BigDecimal) val2) > 0) {
							return 1;
						} else if (((BigDecimal) val1).compareTo((BigDecimal) val2) < 0) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Date) {
						if (((Date) val1).after((Date) val2)) {
							return 1;
						} else if (((Date) val1).before((Date) val2)) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof String) {
						String str1 = AppUtil.cn2py((String) val1);
						String str2 = AppUtil.cn2py((String) val2);
						if ((str1).compareTo(str2) > 0) {
							return 1;
						} else if ((str1).compareTo(str2) < 0) {
							return -1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Boolean) {
						if (((Boolean) val1).compareTo((Boolean) val2) > 0) {
							return 1;
						} else if (((Boolean) val1).compareTo((Boolean) val2) < 0) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			}
		} else if(sortType != null && sortType.equals("DESC")){
			if (grp1 == null && grp2 != null) {
				return 1;
			} else if (grp1 != null && grp2 == null) {
				return -1;
			} else if (grp1 == null && grp2 == null) {
				return 0;
			}
			if (grp1 instanceof Integer) {
				if ((Integer) grp1 > (Integer) grp2) {
					return -1;
				} else if ((Integer) grp1 < (Integer) grp2) {
					return 1;
				} else {
					if (val1 == null && val2 != null) {
						return 1;
					} else if (val1 != null && val2 == null) {
						return -1;
					} else if (val1 == null && val2 == null) {
						return 0;
					}
					if (val1 instanceof Integer) {
						if ((Integer) val1 > (Integer) val2) {
							return -1;
						} else if ((Integer) val1 < (Integer) val2) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Float) {

						if ((Float) val1 > (Float) val2) {
							return -1;
						} else if ((Float) val1 < (Float) val2) {
							return 1;
						} else {
							return 0;
						}

					} else if (val1 instanceof Double) {

						if ((Double) val1 > (Double) val2) {
							return -1;
						} else if ((Double) val1 < (Double) val2) {
							return 1;
						} else {
							return 0;
						}

					} else if (val1 instanceof BigDecimal) {
						if (((BigDecimal) val1).compareTo((BigDecimal) val2) > 0) {
							return -1;
						} else if (((BigDecimal) val1).compareTo((BigDecimal) val2) < 0) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Date) {
						if (((Date) val1).after((Date) val2)) {
							return -1;
						} else if (((Date) val1).before((Date) val2)) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof String) {
						String str1 = AppUtil.cn2py((String) val1);
						String str2 = AppUtil.cn2py((String) val2);
						if ((str1).compareTo(str2) > 0) {
							return -1;
						} else if ((str1).compareTo(str2) < 0) {
							return 1;
						} else {
							return 0;
						}
					}else if (val1 instanceof Boolean) {
						if (((Boolean) val1).compareTo((Boolean) val2) > 0) {
							return -1;
						} else if (((Boolean) val1).compareTo((Boolean) val2) < 0) {
							return 1;
						} else {
							return 0;
						}
					}
				}
			} else if (grp1 instanceof String) {
				String gstr1 = null;
				String gstr2 = null;
				if(sortPinyin){
					gstr1 = AppUtil.cn2py((String) grp1);
					gstr2 = AppUtil.cn2py((String) grp2);
				}else{
					gstr1 = (String) grp1;
					gstr2 = (String) grp2;
				}
				if ((gstr1).compareTo(gstr2) > 0) {
					return -1;
				} else if ((gstr1).compareTo(gstr2) < 0) {
					return 1;
				} else {
					if (val1 == null && val2 != null) {
						return 1;
					} else if (val1 != null && val2 == null) {
						return -1;
					} else if (val1 == null && val2 == null) {
						return 0;
					}
					if (val1 instanceof Integer) {
						if ((Integer) val1 > (Integer) val2) {
							return -1;
						} else if ((Integer) val1 < (Integer) val2) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Float) {

						if ((Float) val1 > (Float) val2) {
							return -1;
						} else if ((Float) val1 < (Float) val2) {
							return 1;
						} else {
							return 0;
						}

					} else if (val1 instanceof Double) {

						if ((Double) val1 > (Double) val2) {
							return -1;
						} else if ((Double) val1 < (Double) val2) {
							return 1;
						} else {
							return 0;
						}

					} else if (val1 instanceof BigDecimal) {
						if (((BigDecimal) val1).compareTo((BigDecimal) val2) > 0) {
							return -1;
						} else if (((BigDecimal) val1).compareTo((BigDecimal) val2) < 0) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof Date) {
						if (((Date) val1).after((Date) val2)) {
							return -1;
						} else if (((Date) val1).before((Date) val2)) {
							return 1;
						} else {
							return 0;
						}
					} else if (val1 instanceof String) {
						String str1 = AppUtil.cn2py((String) val1);
						String str2 = AppUtil.cn2py((String) val2);
						if ((str1).compareTo(str2) > 0) {
							return -1;
						} else if ((str1).compareTo(str2) < 0) {
							return 1;
						} else {
							return 0;
						}
					}else if (val1 instanceof Boolean) {
						if (((Boolean) val1).compareTo((Boolean) val2) > 0) {
							return -1;
						} else if (((Boolean) val1).compareTo((Boolean) val2) < 0) {
							return 1;
						} else {
							return 0;
						}
					}
				}
			}
		}
		return 0;
	}
}
