package com.example.module.login.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.module.login.ILoginContract;
import com.example.module.login.room.User;
import com.example.module.login.room.UserDao;
import com.example.module.login.room.UserDataBase;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginModel implements ILoginContract.Model {
    private final String TAG = "LoginModelTAG";
    private ILoginContract.Presenter mPresenter;
    private Context mContext;

    private static final String fromEmail = "3885512625@qq.com";
    private static final String password = "bvqyxqknthpkcgbg";

    private static long lastSendTime = 0;

    public LoginModel(ILoginContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public int sendVerificationCode(final String destinationEmail, final String verificationCode) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSendTime < TimeUnit.MINUTES.toMillis(1)) {
            Log.d("Email", "请等待一分钟后再尝试发送邮件");
            return 0;
        }

        lastSendTime = currentTime;

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 配置SMTP服务器
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.qq.com");
                props.put("mail.smtp.port", "587");

                // 创建会话
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });
                session.setDebug(true);

                try {
                    // 创建邮件
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setSubject("【农视界】");
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationEmail));
                    message.setText("您的验证码为： " + verificationCode + " ，请勿泄露。");

                    Transport.send(message);
                    Log.d("Email", "邮件发送成功");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.onVerificationCodeSentSuccess();
                        }
                    });

                } catch (MessagingException e) {
                    Log.e("Email", "发送邮件时发生错误: " + e.getMessage());

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.onVerificationCodeSentFailure();
                        }
                    });
                }
            }
        }).start();

        return 1;
    }

    @Override
    public void login(String email, String password, Callback callback) {
        UserDataBase userDataBase = UserDataBase.getINSTANCE(mContext);
        UserDao userDao = userDataBase.userDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "登录：email: " + email + " password: " + password);

                User user = userDao.validateAccount(email, password);
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailure();
                }
            }
        }).start();
    }

    @Override
    public void register(String email, String password, String username, Callback callback) {
        UserDataBase userDataBase = UserDataBase.getINSTANCE(mContext);
        UserDao userDao = userDataBase.userDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (UserDao.class) {
                    Log.d(TAG, "检查邮箱是否已注册：email: " + email);
                    int count = userDao.findUserByEmail(email);
                    if (count > 0) {
                        Log.d(TAG, "邮箱已存在：" + count);
                        callback.onFailure(); // 邮箱已存在，返回失败
                        return;
                    }

                    // 如果邮箱不存在，执行注册操作
                    Log.d(TAG, "注册：email: " + email + " password: " + password + " username: " + username);
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setUserName(username);
                    userDao.insertUser(user);
                    Log.d(TAG, "注册成功：" + user);
                    callback.onSuccess(user);
                }
            }
        }).start();
    }


}
