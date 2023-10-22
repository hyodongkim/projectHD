<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>



<!DOCTYPE html>
<html>
 <body>
    김효동동
    <c:forEach items="${list}" var="hd">
                    <tr>
                        <td>${hd.id}</td>
                        <td>${hd.name}</td>
                    </tr>
    </c:forEach>
 </body>
</html>