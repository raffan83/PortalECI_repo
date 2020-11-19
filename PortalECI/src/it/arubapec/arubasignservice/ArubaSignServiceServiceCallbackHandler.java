
/**
 * ArubaSignServiceServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.7  Built on : Nov 20, 2017 (11:41:20 GMT)
 */

    package it.arubapec.arubasignservice;

    /**
     *  ArubaSignServiceServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ArubaSignServiceServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ArubaSignServiceServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ArubaSignServiceServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for retriveCredential method
            * override this method for handling normal response from retriveCredential operation
            */
           public void receiveResultretriveCredential(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.RetriveCredentialResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from retriveCredential operation
           */
            public void receiveErrorretriveCredential(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendCredential method
            * override this method for handling normal response from sendCredential operation
            */
           public void receiveResultsendCredential(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.SendCredentialResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendCredential operation
           */
            public void receiveErrorsendCredential(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listCertAuth method
            * override this method for handling normal response from listCertAuth operation
            */
           public void receiveResultlistCertAuth(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.ListCertAuthResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listCertAuth operation
           */
            public void receiveErrorlistCertAuth(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pdfsignatureV2_multiple method
            * override this method for handling normal response from pdfsignatureV2_multiple operation
            */
           public void receiveResultpdfsignatureV2_multiple(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureV2_multipleResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pdfsignatureV2_multiple operation
           */
            public void receiveErrorpdfsignatureV2_multiple(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVersion method
            * override this method for handling normal response from getVersion operation
            */
           public void receiveResultgetVersion(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.GetVersionResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVersion operation
           */
            public void receiveErrorgetVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for xmlsignature_multiple method
            * override this method for handling normal response from xmlsignature_multiple operation
            */
           public void receiveResultxmlsignature_multiple(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Xmlsignature_multipleResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from xmlsignature_multiple operation
           */
            public void receiveErrorxmlsignature_multiple(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listprocess method
            * override this method for handling normal response from listprocess operation
            */
           public void receiveResultlistprocess(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.ListprocessResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listprocess operation
           */
            public void receiveErrorlistprocess(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for encryptedEnvelope method
            * override this method for handling normal response from encryptedEnvelope operation
            */
           public void receiveResultencryptedEnvelope(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.EncryptedEnvelopeResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from encryptedEnvelope operation
           */
            public void receiveErrorencryptedEnvelope(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pkcs7Signhash method
            * override this method for handling normal response from pkcs7Signhash operation
            */
           public void receiveResultpkcs7Signhash(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignhashResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pkcs7Signhash operation
           */
            public void receiveErrorpkcs7Signhash(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pkcs7Sign method
            * override this method for handling normal response from pkcs7Sign operation
            */
           public void receiveResultpkcs7Sign(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pkcs7Sign operation
           */
            public void receiveErrorpkcs7Sign(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pkcs7SignV2_multiple method
            * override this method for handling normal response from pkcs7SignV2_multiple operation
            */
           public void receiveResultpkcs7SignV2_multiple(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignV2_multipleResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pkcs7SignV2_multiple operation
           */
            public void receiveErrorpkcs7SignV2_multiple(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for auth_methods method
            * override this method for handling normal response from auth_methods operation
            */
           public void receiveResultauth_methods(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Auth_methodsResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from auth_methods operation
           */
            public void receiveErrorauth_methods(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for closesession method
            * override this method for handling normal response from closesession operation
            */
           public void receiveResultclosesession(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.ClosesessionResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from closesession operation
           */
            public void receiveErrorclosesession(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pkcs7SignV2 method
            * override this method for handling normal response from pkcs7SignV2 operation
            */
           public void receiveResultpkcs7SignV2(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7SignV2ResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pkcs7SignV2 operation
           */
            public void receiveErrorpkcs7SignV2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addpkcs7Sign method
            * override this method for handling normal response from addpkcs7Sign operation
            */
           public void receiveResultaddpkcs7Sign(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Addpkcs7SignResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addpkcs7Sign operation
           */
            public void receiveErroraddpkcs7Sign(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listCert method
            * override this method for handling normal response from listCert operation
            */
           public void receiveResultlistCert(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.ListCertResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listCert operation
           */
            public void receiveErrorlistCert(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pdfsignature method
            * override this method for handling normal response from pdfsignature operation
            */
           public void receiveResultpdfsignature(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pdfsignature operation
           */
            public void receiveErrorpdfsignature(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ping method
            * override this method for handling normal response from ping operation
            */
           public void receiveResultping(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PingResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ping operation
           */
            public void receiveErrorping(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for tsd method
            * override this method for handling normal response from tsd operation
            */
           public void receiveResulttsd(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TsdResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from tsd operation
           */
            public void receiveErrortsd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for m7M method
            * override this method for handling normal response from m7M operation
            */
           public void receiveResultm7M(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.M7MResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from m7M operation
           */
            public void receiveErrorm7M(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for credentials_query method
            * override this method for handling normal response from credentials_query operation
            */
           public void receiveResultcredentials_query(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Credentials_queryResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from credentials_query operation
           */
            public void receiveErrorcredentials_query(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pkcs7Signhash_multiple method
            * override this method for handling normal response from pkcs7Signhash_multiple operation
            */
           public void receiveResultpkcs7Signhash_multiple(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Pkcs7Signhash_multipleResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pkcs7Signhash_multiple operation
           */
            public void receiveErrorpkcs7Signhash_multiple(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for xmlsignature method
            * override this method for handling normal response from xmlsignature operation
            */
           public void receiveResultxmlsignature(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.XmlsignatureResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from xmlsignature operation
           */
            public void receiveErrorxmlsignature(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for signhash method
            * override this method for handling normal response from signhash operation
            */
           public void receiveResultsignhash(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.SignhashResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from signhash operation
           */
            public void receiveErrorsignhash(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pdfsignatureV2 method
            * override this method for handling normal response from pdfsignatureV2 operation
            */
           public void receiveResultpdfsignatureV2(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.PdfsignatureV2ResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pdfsignatureV2 operation
           */
            public void receiveErrorpdfsignatureV2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verifyOtp method
            * override this method for handling normal response from verifyOtp operation
            */
           public void receiveResultverifyOtp(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.VerifyOtpResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyOtp operation
           */
            public void receiveErrorverifyOtp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verify method
            * override this method for handling normal response from verify operation
            */
           public void receiveResultverify(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.VerifyResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verify operation
           */
            public void receiveErrorverify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for test_otp method
            * override this method for handling normal response from test_otp operation
            */
           public void receiveResulttest_otp(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.Test_otpResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from test_otp operation
           */
            public void receiveErrortest_otp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for tsr method
            * override this method for handling normal response from tsr operation
            */
           public void receiveResulttsr(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.TsrResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from tsr operation
           */
            public void receiveErrortsr(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for opensession method
            * override this method for handling normal response from opensession operation
            */
           public void receiveResultopensession(
                    it.arubapec.arubasignservice.ArubaSignServiceServiceStub.OpensessionResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from opensession operation
           */
            public void receiveErroropensession(java.lang.Exception e) {
            }
                


    }
    