<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LinkShare: Edit submitted link</title>
<script
    src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
</head>
<body>
    <h1>Edit Link</h1>
    <h2>Link: </h2>
    <p>
        ${link.url}
    </p>
    
    <form action="/edit" method="POST">
        <input type="hidden" name="url" value="${link.url}" />
        <p>
            Title: <input type="text" name="title" value="${link.title}"/>
        </p>        
        <p>
            Summary: <input type="text" name="summary" value="${link.summary}"/>
        </p>        
        
        <input type="submit" />
    </form>
</body>
</html>