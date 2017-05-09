<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class='percentageList chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>短信价格</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' id="form1">
            <div class="form-group">
                <label for="telph" class='listLab'>国家(中文)：</label>
                <input type="text" class="form-control listInp" id="country_cn" name="country_cn" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>国家(英文)：</label>
                <input type="text" class="form-control listInp" id="country_en" name="country_en" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>国家代码：</label>
                <input type="text" class="form-control listInp" id="country_code" name="country_code" placeholder="">
            </div>
            <button type="button" class="btn btn-default listBtn" onclick="search()" >查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="exportPOI()" >下载</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th>国家(中文)</th>
						<th>国家(英文)</th>
						<th>国家代码</th>
						<th>国际价格</th>
					</tr>
				</thead>
        </table>
    </div>
