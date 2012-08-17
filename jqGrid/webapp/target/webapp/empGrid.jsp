<%@ page import="poc.flexjson.model.Employee" %>
<%@ page import="com.google.common.collect.Lists" %>
<%@ page import="poc.flexjson.model.Phone" %>
<%@ page import="flexjson.JSONSerializer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Employee Listing</title>

    <link rel="stylesheet" type="text/css" media="screen"
          href="resources/jquery/jqgrid/theme/redmond/jquery-ui-1.8.2.custom.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="resources/jquery/jqgrid/css/ui.jqgrid.css"/>

    <script src="resources/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
    <script src="resources/jquery/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
    <script src="resources/jquery/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>

    <%
        Employee emp = new Employee("Amit", "Kapoor")
                .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));
        Employee emp2 = new Employee("Tim", "Johnes");
        Employee emp3 = new Employee("Tim", "Pollock");
        String empsJson = new JSONSerializer().exclude("*.class", "fullName").serialize(Lists.<Object>newArrayList(emp, emp2, emp3));
//        String empsJson = new JSONSerializer().exclude("*.class").serialize(Lists.<Object>newArrayList());

    %>
    <script type="text/javascript">

        var employeeData = <%=empsJson%>;

        jQuery(document).ready(function() {
            $("#grid").jqGrid(
            {     data: employeeData
                , datatype: "local"
                , height: -1, rowNum: 10000
                , rowList: []
                , colNames:['First Name','Last Name']
                , colModel:[
                {name:'firstName',index:'firstName'},
                {name:'lastName',index:'lastName'}
            ]
                , pager: "#pager", viewrecords: false, caption: "Manipulating Array Data", altRows: true
                , rownumbers: true, gridview: true
                , multiselect: true, sortname: "lastName", emptyrecords: "Nothing to display", pgbuttons: false
                , pginput: false
            });

//            $("#grid").jqGrid('navGrid', '#pager', {del:true,add:true,edit:true,search:true});
            $("#grid").jqGrid('filterToolbar', {stringResult: true,searchOnEnter : false});

            tableToGrid("#mytable", {pager: "tablePager", caption: "Table to Grid", altRows: true
                                    , rownumbers: true, viewrecords: true, sortname: "Header2"
            });
            tableToGrid("#emptyTable", {pager: "emptyTablePager", caption: "Empty Table", altRows: true
                                    , rownumbers: true, viewrecords: true, sortname: "Header2", emptyrecords: "No Records Found"
            });
        });

    </script>

</head>
<body>
Sample Invoice Grid
<table id="grid"></table>
<div id="pager"></div>

<div >
    <table id="mytable" border="1">
        <thead>
        <tr>
            <th>Header1</th>
            <th>Header2</th>
            <th>Header3</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><a href="#">Cell 11</a> </td>
            <td>Cell 12</td>
            <td>Cell 13</td>
        </tr>
        <tr>
            <td><a href="#">Cell 31</a></td>
            <td>Cell 32</td>
            <td>Cell 33</td>
        </tr>
        <tr>
            <td><a href="#">Cell 21</a></td>
            <td>Cell 22</td>
            <td>Cell 23</td>
        </tr>
        </tbody>
    </table>
    <div id="tablePager"></div>

    <table id="emptyTable" border="1">
        <thead>
        <tr>
            <th>Header1</th>
            <th>Header2</th>
            <th>Header3</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div id="emptyTablePager"></div>
</div>

</body>
</html>