package com.xz.cenco.weed.txapp;

/**
 * Created by Administrator on 2018/6/11.
 *   if ((!paramAnonymousBaseViewHolder.equals("1")))
 break label135;
 localTextView.setText("未支付，正在支付中");
 }
 if (paramAnonymousBaseViewHolder.equals("2"))
 {
 localTextView.setText("已结算");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("3"))
 {
 localTextView.setText("帐号不是手机号或email，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("4"))
 {
 localTextView.setText("帐号未注册支付宝，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("5"))
 {
 localTextView.setText("支付宝帐号未激活，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("6"))
 {
 localTextView.setText("帐号未实名认证激活冻结，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("7"))
 {
 localTextView.setText("未知原因1，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("8"))
 {
 localTextView.setText("支付失败-请开通VIP");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("9"))
 {
 if (DetailActivity.this.loadData.getVip().equals("0"))
 {
 localTextView.setText("普通用户 24 小时限制，提现失败");
 return;
 }
 localTextView.setText("VIP" + DetailActivity.this.loadData.getVip() + ":" + Integer.parseInt(DetailActivity.this.vipData.getTxspantime()) / 60 + "小时限制，提现失败");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("10"))
 {
 localTextView.setText("收款帐号异常，不能收款");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("11"))
 {
 localTextView.setText("未完善身份信息或没有余额账户");
 return;
 }
 if (paramAnonymousBaseViewHolder.equals("12"))
 {
 localTextView.setText("公司类型账户须通过认证才可收款");
 return;
 }
 }
 while (!paramAnonymousBaseViewHolder.equals("13"));
 localTextView.setText("可能姓名校验错误，提现失败");
 }
 */
public class TxRecord {
    public int id;
    public int txend;
    public String user;
    public String txyhk;
    public String txzh;
    public String txje;
    public String txxm;
    public String vip;
    public String mac;
    public String ip;
    public String txtime;
    public String endtime;
    public String app;

    @Override
    public String toString() {
        return "提现时间:"+txtime+",姓名:"+txxm+",入侵账户:"+user+",状态:"+txend;
    }
}
