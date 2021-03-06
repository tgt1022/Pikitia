package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Benjamin Schock
 * 
 */

public class GuestPurchaseDAOImpl extends AbstractDAO {

	@Transactional
	public void saveGuestPurchase(GuestPurchase guestPurchase) {
		sessionFactory.getCurrentSession().saveOrUpdate(guestPurchase);
	}

	@Transactional
	public GuestPurchase getGuestPurchaseByID(int idGuestPurchase) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT gp FROM GuestPurchase gp where gp.idGuestPurchase = " + idGuestPurchase);
		return (GuestPurchase) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<GuestPurchase> getAllGuestPurchases() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT gp FROM GuestPurchase gp");
		return (List<GuestPurchase>) query.list();
	}
	
	@Transactional
	public Integer getLastInsertedGuestPurchaseID() {
		List<Integer> results = sessionFactory.getCurrentSession()
				.createCriteria(GuestPurchase.class)
				.setProjection(Projections.max("idGuestPurchase")).list();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
}