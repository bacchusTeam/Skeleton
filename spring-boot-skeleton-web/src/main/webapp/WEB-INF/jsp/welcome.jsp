<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" src="<c:url value="/script/common.js" />"></script>
</head>
<body>
<c:url value="/resources/text.txt" var="url"/>
<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl"/>
Spring URL: ${springUrl} at ${time}
<br>
JSTL URL: ${url}
<br>
Message: ${message}
<br>
제발
</body>
</html>
