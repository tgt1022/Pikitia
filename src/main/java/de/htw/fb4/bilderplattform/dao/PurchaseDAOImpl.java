package de.htw.fb4.bilderplattform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Peter Horn
 * 
 */
public class PurchaseDAOImpl extends AbstractDAO {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Purchase_Image> getAllPurchasesByUserId(int idUser) {
		List<Purchase_Image> purchases = new ArrayList<Purchase_Image>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Purchase_Image.class);
			purchases = criteria.list();
			List<Purchase_Image> user_purchases = new ArrayList<Purchase_Image>();
			// get users purchases only
			for (Purchase_Image p : purchases) {
				if (p.getImage().getUser().getIdUser() == idUser) {
					user_purchases.add(p);
				}
			}
			purchases = user_purchases;
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchases;
	}

	@Transactional
	public GuestPurchase getGuestPurchaseById(int idGuestPurchase) {
		GuestPurchase guestPurchase = new GuestPurchase();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(GuestPurchase.class)
					.add(Restrictions.eq("idGuestPurchase", idGuestPurchase));
			guestPurchase = (GuestPurchase) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return guestPurchase;
	}
	
	@Transactional
	public User getUserByIdUserPurchase(int idUserPurchase) {
		// that should be it
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPurchase.class)
				.add(Restrictions.eq("idUserPurchase", idUserPurchase));
		return ((UserPurchase) criteria.uniqueResult()).getUser();
//		User user = new User();
//		Session session = sessionFactory.getCurrentSession();
//		try {
//			Criteria criteria = session.createCriteria(UserPurchase.class)
//					.add(Restrictions.eq("idUserPurchase", idUserPurchase));
//			int idUser = ((UserPurchase) criteria.uniqueResult()).getUser_idUser();
//			criteria = session.createCriteria(User.class)
//					.add(Restrictions.eq("idUser", idUser));
//			user= (User) criteria.uniqueResult();
//		} catch (DataAccessException dae) {
//			session.getTransaction().rollback();
//		}
//		return user;
	}
	
	@Transactional
	public Purchase getPurchaseById(int idPurchase) {
		Purchase purchase = new Purchase();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Purchase.class)
					.add(Restrictions.eq("idPurchase", idPurchase));
			purchase = (Purchase) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchase;
	}
	
	@Transactional
	public Bankaccount getBankaccountByIdUser(int idUser){
		Bankaccount bank = new Bankaccount();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Bankaccount.class).createCriteria("bankaccountOwner")
					.add(Restrictions.eq("idUser", idUser));
			bank = (Bankaccount) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return bank;
	}
	
	@Transactional
	public void savePurchase(Purchase purchase) {
		sessionFactory.getCurrentSession().saveOrUpdate(purchase);
	}
	
	@Transactional
	public UserPurchase getUrlIdUserPurchase(String urlId) {
		/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPurchase.class)
				.add(Restrictions.eq("urlId", urlId));
		if (criteria.uniqueResult() != null){
			return ((UserPurchase) criteria.uniqueResult());
		} else {
			return null;
		}*/
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT up FROM UserPurchase up where up.urlId like '" + urlId + "'");
		return (UserPurchase) query.uniqueResult();
		
	}
	
	@Transactional
	public GuestPurchase getUrlIdGuestPurchase(String urlId) {
		/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPurchase.class)
				.add(Restrictions.eq("urlId", urlId));
		if (criteria.uniqueResult() != null){
			return ((GuestPurchase) criteria.uniqueResult());
		} else {
			return null;
		}*/
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT up FROM GuestPurchase up where up.urlId like '" + urlId + "'");
		return (GuestPurchase) query.uniqueResult();
	}
	
	@Transactional
	public int getPurchaseIdByGuestPurchase(GuestPurchase guestPurchase) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT up.idPurchase FROM Purchase up where up.GuestPurchase_idGuestPurchase = " + guestPurchase.getIdGuestPurchase());
		/*
		System.out.println("GGGGGGGG");
		System.out.println(query.toString());
		int returnValue = (int) query.uniqueResult();
		System.out.println(returnValue);
		return returnValue;
		*/
		return (int) query.uniqueResult();		
	}
	
	@Transactional
	public int getPurchaseIdByUserPurchase(UserPurchase userPurchase) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT up.idPurchase FROM Purchase up where up.UserPurchase_idUserPurchase = " + userPurchase.getIdUserPurchase());
		/*
		System.out.println("UUUUUUU");
		System.out.println(query.toString());
		int returnValue = (int) query.uniqueResult();
		System.out.println(returnValue);
		return returnValue;
		*/
		return (int) query.uniqueResult();
	}
	
	@Transactional
	public List<Purchase_Image> getPurchasesByPurchaseId(int purchaseId) {
		List<Purchase_Image> purchases = new ArrayList<Purchase_Image>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Purchase_Image.class);
			purchases = criteria.list();
			List<Purchase_Image> user_purchases = new ArrayList<Purchase_Image>();
			// get users purchases only
			for (Purchase_Image p : purchases) {
				if (p.getPurchase().getIdPurchase() == purchaseId) {
					user_purchases.add(p);
				}
			}
			purchases = user_purchases;
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchases;
	}
	
}
