<%--
  Created by IntelliJ IDEA.
  User: hejian
  Date: 2014/7/14
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title>debug</title>
    <script src="${ctx}/js/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/j-query.json-2.4.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            /**
            *使用jquery发送json格式的数据。使用$.ajax()方法。其它如$.getJSON()等方法会将发送类容
            *会被转换成查询字符串。例如：
            * http://localhost/service/device/management/terminal/authentication?{%22userPhone%22:%22%22,
            *      %22userName%22:%22%22,%22uniqueID%22:%2200000000-3cdb-e722-ffff-ffffd6d066c7%22}
            *
            * $.ajax()方法介绍：
            *
            *contentType：发送信息至服务器时内容编码类型。默认:application/x-www-form-urlencoded
            *
            *dataType:预期服务器返回的数据类型。如果不指定，jQuery 将自动根据 HTTP
            *         包 MIME 信息来智能判断，比如XML MIME类型就被识别为XML。
            *         常用类型：
            *         "xml": 返回 XML 文档，可用 jQuery 处理。
            *
            *         "html": 返回纯文本 HTML 信息；包含的script标签会在插入dom时执行。
            *
            *         "script": 返回纯文本 JavaScript 代码。不会自动缓存结果。除非设置了"cache"参数。
            *                  '''注意：'''在远程请求时(不在同一个域下)，所有POST请求都将转为GET请求。
            *                  (因为将使用DOM的script标签来加载)。
            *
            *         "json": 返回 JSON 数据 。
            *
            *         "jsonp": JSONP 格式。使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery
            *                将自动替换 ? 为正确的函数名，以执行回调函数。
            *
            *         "text": 返回纯文本字符串
            */

            var requestParam = {
                "userPhone": "",
                "userName": "",
                "uniqueID": "00000000-3cdb-e722-ffff-ffffd6d066c7",
                "deviceID": "设备 ID",
                "requestType": "TerminalAuthentication",
                "randomKey": "1234",
                "params": [
                    "有限 MAC 地址",
                    "无线 MAC 地址",
                    "盒子机器码",
                    "地理位置信息"
                ],
                "password": ""
            };

            $("#mybutton").click(function(){
                var jsonStr = $.toJSON(requestParam);   ////调用j-query.json 库，将其转换为json
                $.ajax({
                    url:"${ctx}/service/device/management/terminal/authentication",
                    type:"POST",
                    data:jsonStr,
                    contentType:'application/json;charset=utf8',
                    dataType:"json",
                    success:function(responseData){
                        alert(responseData);
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        alert("错误");
                    }
                });
            });

        })
    </script>
</head>

<body>
    <h2>debug page.</h2>

    <input type="button" id="mybutton" value="设备认证">

</body>
</html>
