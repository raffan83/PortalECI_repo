<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:layout title="Login Page" bodyClass="hold-transition login-page">
	<jsp:attribute name="body_area"> 

		<form id="loginForm" name="frmLogin" method="post" action="">

			<div class="login-box">
  				
  				<div class="login-logo">
    				<img src="./images/logo_ecispa.png" style="width: 50%">
  				</div>
  				<!-- /.login-logo -->
  				<div class="login-box-body">
    				<p class="login-box-msg">Accedi per iniziare la tua sessione</p>
 					<div class="row">
  						<div class="col-xs-12">
       						<div class="form-group has-feedback control-group">
								<input type="text" name="uid" type="text" class="form-control" value="" placeholder="username" id="user" required aria-invalid="false" />
        						<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
								<p class="help-block with-errors"></p>
      						</div>
      					</div>
  						<div class="col-xs-12">
      						<div class="form-group has-feedback">
        						<input type="password" name="pwd" type="password" class="form-control" value="" placeholder="password" id="pass" required aria-invalid="false" />
        						<span class="glyphicon glyphicon-lock form-control-feedback"></span>
         						<p class="help-block with-errors"></p>
      						</div>
      						<c:if test="${not empty errorMessage}">
    							<div id="erroMsg" class="form-group has-error">
    								<label class="control-label" for="inputError">
                    					${errorMessage}
                    				</label>                 
              					</div>
							</c:if>
      					</div>
        				<div class="col-xs-12">      
       						<div class="form-group">
                				<button id="submitLogin" class="btn btn-danger btn-block btn-flat" onclick="Controllo()">Login</button>
      						</div>
           				</div>
        				<div class="col-xs-12 centered">
       						<div class="form-group">
        						<a id="submitLogin" href="#" class="btn btn-primary btn-block btn-flat">Recupera Password</a>
       						</div>  
       					</div>
					</div>
				</div>
			</div>
			<!-- /.login-box-body -->
		</form>
	</jsp:attribute>

	<jsp:attribute name="extra_js_footer"> 

		<script>
			$( document ).ready(function() {
		  		$('#loginForm').validator(); 
	  	  		$( "input" ).keydown(function() {
	  				$('#erroMsg').html('');
	  			});
			});
		</script>

	</jsp:attribute>

</t:layout>



