package com.xz.cenco.weed.txapp;

/**
 * Created by Administrator on 2018/6/9 0009.
 * if((status == null) || (status.equals("1"))) {
 textView.setText("未支付，正在支付中");
 return;
 }
 if(status.equals("2")) {
 textView.setText("已结算");
 return;
 }
 if(status.equals("3")) {
 textView.setText("帐号不是手机号或email，提现失败");
 return;
 }
 if(status.equals("4")) {
 textView.setText("帐号未注册支付宝，提现失败");
 return;
 }
 if(status.equals("5")) {
 textView.setText("支付宝帐号未激活，提现失败");
 return;
 }
 if(status.equals("6")) {
 textView.setText("帐号未实名认证激活冻结，提现失败");
 return;
 }
 if(status.equals("7")) {
 textView.setText("未知原因1，提现失败");
 return;
 }
 if(status.equals("8")) {
 textView.setText("支付失败-请开通VIP");
 return;
 }
 if(status.equals("9")) {
 if(loadData.getVip().equals("0")) {
 textView.setText("普通用户 24 小时限制，提现失败");
 return;
 }
 textView.setText("VIP" + loadData.getVip() + ":" + (Integer.parseInt(vipData.getTxspantime()) / 0x3c) + "小时限制，提现失败");
 return;
 }
 if(status.equals("10")) {
 textView.setText("收款帐号异常，不能收款");
 return;
 }
 if(status.equals("11")) {
 textView.setText("未完善身份信息或没有余额账户");
 return;
 }
 if(status.equals("12")) {
 textView.setText("公司类型账户须通过认证才可收款");
 return;
 }
 if(status.equals("13")) {
 textView.setText("可能姓名校验错误，提现失败");
 }
 */
public class WithdrawItem {
}
