package com.nhsoft.module.report;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.dialect.SQLServer2008Dialect;

public class SQLServer2008R2Dialect extends SQLServer2008Dialect {

	@Override
	public String appendLockHint(LockOptions lockOptions, String tableName) {
		if ( lockOptions.getLockMode() == LockMode.UPGRADE_NOWAIT ) {
			return tableName + " with (updlock, rowlock, nowait)";
		}
		// READ mode 设置 nolock 选项
		if ( lockOptions.getLockMode() == LockMode.READ ) {
			return tableName + " with (nolock) ";
		}
		final LockMode mode = lockOptions.getLockMode();
		final boolean isNoWait = lockOptions.getTimeOut() == LockOptions.NO_WAIT;
		final String noWaitStr = isNoWait ? ", nowait" : "";
		switch ( mode ) {
			case UPGRADE:{
				return tableName + " with (updlock, rowlock)";
			}
			case NONE:{
				return tableName + " with (nolock)";
			}
			case PESSIMISTIC_WRITE: {
				return tableName + " with (updlock, rowlock" + noWaitStr + ")";
			}
			case WRITE: {
				return tableName + " with (updlock, rowlock" + noWaitStr + ")";
			}
			case PESSIMISTIC_READ: {
				return tableName + " with (holdlock, rowlock" + noWaitStr + ")";
			}case UPGRADE_SKIPLOCKED:
				return tableName + " with (updlock, rowlock, readpast" + noWaitStr + ")";
			default: {
				return tableName;
			}
		}
	}
	
	

}
