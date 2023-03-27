package com.CustomRequestListener.spring.requestListener;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.transaction.Context;

import com.CustomRequestListener.spring.specialenum.SpecialEnum;

public class RequestListener implements ISORequestListener, Configurable {

	private String queueName;
    protected Space<String, Context>  sp;

    public static final String REQUEST = "REQUEST";
    public static final String ISOSOURCE = "ISOSOURCE";
        
        public RequestListener() {
		super();
		// TODO Auto-generated constructor stub
	}

		@Override
        public void setConfiguration(Configuration cfg)
                throws ConfigurationException {
           
           
            queueName = cfg.get("queue");
            System.out.println("------------------"+queueName);

           
             sp =  SpaceFactory.getSpace (cfg.get ("space"));   
           
            System.out.println("space--------------"+sp.toString());
           
        }
       
        public boolean process (ISOSource source, ISOMsg m) {
            System.out.println("Processed method--------");

        Context ctx = new Context ();
        System.out.println("keeping the message in request");
        ctx.put (SpecialEnum.request, m);
       
        System.out.println("keeping the source in the ISOSource");
        ctx.put (SpecialEnum.isosource, source);
        System.out.println("adding the context to space");
        sp.out (queueName, ctx);
        return true;
        }
}
