package com.shnok.javaserver.db.repository;

import com.shnok.javaserver.db.DbFactory;
import com.shnok.javaserver.db.entity.DBAccountInfo;
import com.shnok.javaserver.db.interfaces.AccountInfoDao;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

@Log4j2
public class AccountInfoRepository implements AccountInfoDao {
    private static AccountInfoRepository instance;
    public static AccountInfoRepository getInstance() {
        if (instance == null) {
            instance = new AccountInfoRepository();
        }
        return instance;
    }

    @Override
    public DBAccountInfo getAccountInfo(String login) {
        try (Session session = DbFactory.getSessionFactory().openSession()) {
            return session.createQuery("SELECT i FROM DBAccountInfo i WHERE login=:login", DBAccountInfo.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void createAccount(DBAccountInfo accountInfo) {
        try (Session session = DbFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(accountInfo);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQL ERROR: {}", e.getMessage(), e);
        }
    }

    @Override
    public void updateAccount(DBAccountInfo accountInfo) {
        try (Session session = DbFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(accountInfo);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQL ERROR: {}", e.getMessage(), e);
        }
    }

    @Override
    public void updateAccountLastServer(String account, int serverId) {
        try (Session session = DbFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("UPDATE DBAccountInfo i SET lastServer=:lastServer WHERE login=:login")
                    .setParameter("login", account)
                    .setParameter("lastServer", serverId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQL ERROR: {}", e.getMessage(), e);
        }
    }
}
