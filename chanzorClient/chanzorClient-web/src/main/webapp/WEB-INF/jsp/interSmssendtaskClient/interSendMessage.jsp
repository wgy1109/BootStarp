<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

   <div class='messManage chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>发送短信</p>
        </div>
        <form method="post" id="payPostInfoForm" class="form-horizontal">
            <div class='messManage_top1'>
                <div class='messManage_top1_left'><span>发送应用：</span></div>
                <div class='messManage_top1_right'>
                    <select id="spId" name="spId" class='messManage_top1_right_inp messManage_top1_right_inp1'></select>
                </div>
            </div>
            <div class='messManage_top2'>
                <div class='messManage_top2_left'>
                    <span>手机号码：</span>
                </div>
                <div class='messManage_top2_right'>
                    <textarea id="mobile" name="mobile" rows="10" cols="55" placeholder="请输入手机号码" class="textareawidth"></textarea>
                    <p><span id="mobileLayerMsg" style='color:red;'></span></p>
                    <p style="padding: 10px 0 0 0;"><span><a href="javascript:;" onclick="downLoadTemplete('')">国家代码下载</a></span>
                    <button type="button" class="btn btn-primary messManage_btn" style="margin-left:200px" onclick="exportPhone()">批量导入</button></p>
                </div>
                <div class="promptMessage">
	            	<h2>注意：</h2><br/>
					<dl>
						<dd>1、国际短信无加载签名的强制要求。</dd>
						<dd>2、不能发送中奖、抽奖、发票等短信内容。</dd>
						<dd>3、短信字数按照70个字数一条短信计算。纯英文字符按照150个字数一条计算。</dd>
						<dd>4、短信计费长度=内容+追加的字符。</dd>
						<dd>5、单次提交最多不超过5万个号码。号码超过2千时请通过批量导入。</dd>
					</dl>
	            </div>
            </div>
            <div class='messManage_top3'>
                <div class='messManage_top3_left'><span>短信内容：</span></div>
                <div class='messManage_top3_right'>
                    <div id="priceHtml"><textarea cols="55" rows="10" id="plateInfocontent" name="content" placeholder="请输入短信内容" class="textareawidth"></textarea></div>
                    <p><input type="hidden" id="hiddenSignature" ></p> 
                    <p><span id="resultcheckword" style='color:red;'></span></p> 
					<p style="padding:10px 0 0 0 "><span>注:短信内容长度（<var id="smsLength">0</var>），短信计费长度（<var id="smsCount">0</var>），计费条数（<var id="smsChargeNum">0</var>） </span></p>
                    <p style="padding:10px 0 0 0 ">
                        <span>定时发送时间：</span><input type="search" id="timingTime" name="timingTime" class="messManage_top3_rightSp" > <span style='color:red;'>(若为空，表示立即发送)</span>
                    </p>
                    <p>
                        <button type="submit" class="btn btn-primary messManage_sub" >提交</button>
                        <button type="reset" class="btn btn-primary messManage_sub" onclick="delCount()">重置</button>
                    </p>
                </div>
            </div>
        </form>
    </div>

<div id="modaljsp" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="blacktitle" class="modal-title">批量导入手机号</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="impotPhoneListForm" method="post">
					<input class="form-control" type="hidden" id="id" name="ID" />
					<div class="form-group" id="fileArea">
						<label class="col-md-4 control-label">选择文件（excel、txt）：</label>
						<div class="col-md-7">
							<input id="file" type="file" filename="PHONEINFO" name="file"
								style="margin-top:6px">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 col-md-offset-4">
							<a href="javascript:;" onclick="downLoadTemplete('excel')">Excel模板下载</a>
						</div>
						<div class="col-md-3">
							<a href="javascript:;" onclick="downLoadTemplete('txt')">txt模板下载</a>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-primary" type="submit">确定</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>