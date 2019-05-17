<@app_layout>
	<form method="POST" action="<@spring.url '/users' />" enctype="multipart/form-data">
		<input type="text" name="username" placeholder="Username"><br><br>
		<input type="password" name="password" placeholder="Password"><br><br>
		<input type="file" name="file"><br><br>
		<button type="submit">Register</button>
	</form>
</@app_layout>