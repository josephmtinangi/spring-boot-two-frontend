		<script src="<@spring.url '/assets/js/pages/sweetalert.min.js' />"></script>
		<script>
		    	 <#if successMessage??>
		       	 swal({
		           title: "${successMessage?upper_case}",
		            	type: "success"
		         		 });
		    	</#if>
		    		 
		    	<#if errorMessage??>
		    	   swal({
		    	       title: "${errorMessage?upper_case}",
		    	       type: "warning"
		    	   });
		    	</#if>
		</script>

	</body>
</html>