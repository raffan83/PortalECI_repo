
/**
 * TypeOfTransportNotImplementedException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.7  Built on : Nov 20, 2017 (11:41:20 GMT)
 */

package it.arubapec.arubasignservice;

public class TypeOfTransportNotImplementedException extends java.lang.Exception{

    private static final long serialVersionUID = 1523613092333L;
    
    private it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TypeOfTransportNotImplementedE faultMessage;

    
        public TypeOfTransportNotImplementedException() {
            super("TypeOfTransportNotImplementedException");
        }

        public TypeOfTransportNotImplementedException(java.lang.String s) {
           super(s);
        }

        public TypeOfTransportNotImplementedException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public TypeOfTransportNotImplementedException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TypeOfTransportNotImplementedE msg){
       faultMessage = msg;
    }
    
    public it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TypeOfTransportNotImplementedE getFaultMessage(){
       return faultMessage;
    }
}
    