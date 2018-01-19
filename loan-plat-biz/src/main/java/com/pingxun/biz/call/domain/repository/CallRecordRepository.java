package com.pingxun.biz.call.domain.repository;

import com.pingxun.biz.call.domain.entity.CallRecord;
import com.pingxun.core.common.base.BaseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
* @Title: CallRecordRepository.java
* @Description:  拨打记录自定义操作库
* @author Away
* @date 2018/1/9 18:27
* @copyright 重庆平讯数据
* @version V1.0
*/
public interface CallRecordRepository extends BaseRepository<CallRecord,Long>{

}
