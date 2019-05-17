<@app_layout>
	<a href="<@spring.url '/users/create' />">Register</a>
	<table border="1">
		<tbody>
			<#list 0..< data.length() as key>
				<tr>
					<td>${data.getJSONObject(key).id?string.computer}</td>
					<td>${data.getJSONObject(key).username!''}</td>
					<td><img src="${attachments_path}/${data.getJSONObject(key).photo!''}" height="50px" width="50px" alt=""></td>
					<td>${data.getJSONObject(key).createdAt}</td>
					<td>Edit</td>
					<td>Delete</td>
				</tr>
			</#list>
		</tbody>
	</table>
</@app_layout>