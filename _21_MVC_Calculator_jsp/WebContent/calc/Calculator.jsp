<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
</head>
<body>
	<h2>jsp 계산기_2</h2>
	<form action="Calculator.jsp" method="get">
		<input type="text" name="v1" size="4" value="">
		<select name="op">
			<option value="+">+</option>
			<option value="-">-</option>
			<option value="*">*</option>
			<option value="/">/</option>
		</select>
		<input type="text" name="v2" size="4" value="">
		<input type="submit" value="=">
		<input type="text" size="8" value="">
	</form>
</body>
</html>







