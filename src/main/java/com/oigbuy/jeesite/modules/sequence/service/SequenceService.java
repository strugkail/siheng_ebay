package com.oigbuy.jeesite.modules.sequence.service;

import com.oigbuy.jeesite.modules.sequence.dao.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("sequenceService")
@Transactional
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;
	private static Integer synObj = new Integer(1);

	private static long seq = 0 ;
	private static long productCodeSeq = 0 ;

	/**
	 * 创建全局ID
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized Long createGlobalId(){
		int step = 500;
		int tstep = 0;
		Long res = 0L;
		if (seq % step == 0) {
			if (seq == 0) {
				sequenceDao.updateGlobalId(step);
				seq = sequenceDao.getGlobalId();
				tstep =(int) (seq % step);
				if (tstep != 0) {
					sequenceDao.updateGlobalId(step - tstep);
					seq = sequenceDao.getGlobalId();
				}
				seq = seq - step + 1;
				res = seq;
				seq++;
			} else {
				res = seq;
				sequenceDao.updateGlobalId(step);
				seq = sequenceDao.getGlobalId();
				seq = seq - step + 1;
			}

		} else {
			res = seq;
			seq++;
		}

		return res;

	}
	/**
	 * 创建产品代码
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long createProductCode(){
		synchronized (synObj) {
			int step = 100;
			int tstep = 0;
			long temp;
			Long res = 0L;
			if (productCodeSeq % step == 0) {
				if (productCodeSeq == 0) {
					sequenceDao.updateProductCode(step);
					temp = sequenceDao.getProductCode();
					tstep =(int) (temp % step);
					if (tstep != 0) {
						sequenceDao.updateProductCode(step - tstep);
						temp = sequenceDao.getProductCode();
					}
					productCodeSeq = temp - step + 1;
					res = productCodeSeq;
					productCodeSeq++;
				} else {
					res = productCodeSeq;
					sequenceDao.updateProductCode(step);
					productCodeSeq = sequenceDao.getProductCode();
					productCodeSeq = productCodeSeq - step + 1;
				}

			} else {
				res = productCodeSeq;
				productCodeSeq++;
			}

			return res;
		}

//		sequenceDao.updateProductCode();
//		return sequenceDao.getProductCode();
	}
}
