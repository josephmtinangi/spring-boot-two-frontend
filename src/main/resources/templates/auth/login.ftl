<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Login</title>
</head>
<body>
	
	<form action="<@spring.url '/login' />" method="POST">
		<input type="text" name="username" placeholder="Username"> <br><br>
		<input type="password" name="password" placeholder="password"> <br><br>
		<button type="submit">Login</button>
	</form>

</body>
</html>