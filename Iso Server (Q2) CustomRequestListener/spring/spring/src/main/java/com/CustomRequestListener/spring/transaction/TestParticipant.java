package com.CustomRequestListener.spring.transaction;

import java.io.Serializable;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import com.CustomRequestListener.spring.specialenum.SpecialEnum;

	public class TestParticipant implements TransactionParticipant { 

	    public void abort(long id, Serializable context) { 
	            System.out.println("ParticipantTest Process aborted"); 
	    } 

	    public void commit(long id, Serializable context) { 
	        Context ctx=(Context)context; 
	        ISOMsg msg=(ISOMsg)ctx.get(SpecialEnum.request);
	        ISOSource source=(ISOSource)ctx.get(SpecialEnum.isosource); 
	        try { 
	        	source.send(msg); 
	        } catch(Exception e) { 
	        	e.printStackTrace(); 
	        } 
	        System.out.println("Committing the participantTest process"); 
	    } 

	    public int prepare(long id, Serializable context) { 
	        Context ctx=(Context)context; 
	        ISOMsg msg=(ISOMsg)ctx.get(SpecialEnum.request); 
	        Logger logger=new Logger(); 
	        logger.addListener (new SimpleLogListener (System.out)); 
	        ISOSource source=(ISOSource)ctx.get(SpecialEnum.isosource);
	        try { 
	        	msg.setResponseMTI();
	        	msg.set(39,"00"); 
	        	source.send(msg); 
	        } catch(Exception e) { 
	        	e.printStackTrace(); 
	        } 
	        return PREPARED; 
	    } 

	}