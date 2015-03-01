package dao;

import java.util.ArrayList;
import java.util.List;











import util.HibernateUtil;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

public class RouteDAOImpl extends GenericDAOImpl<Integer, Route> implements RouteDAO {

	public List<Route> findRouteFromAtoB(Station A, Station B) {
		String hql = "SELECT r FROM Route r,Route r2 "
					+ "WHERE r.trainId=r2.trainId "
					+ "and r.stationId=:stationA "
					+ "and r2.stationId=:stationB "
					+ "and r.stationOrdinalNumber<r2.stationOrdinalNumber";
		List<Route> routeList = HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("stationA", A)
				.setParameter("stationB", B)
				.list();
		return routeList;
	}

	public List<Station> findStationsByTrain(int id) {
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId.id=:train "
					+ "ORDER BY r.stationOrdinalNumber";
		List<Station> stationList = HibernateUtil.getCurrentSession().createQuery(hql)
			.setParameter("train", id)
			.list();
		return stationList;
	}

	public int getOrdinalNumber(Station stationA) {
		String hql = "SELECT r.stationOrdinalNumber FROM Route r WHERE r.stationId=:stationA";
		int number = (Integer) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("stationA", stationA)
				.uniqueResult();
		return number;
	}

	public List<Station> findStationsBetweenFromAndTo(Run run, int stationFromOrdinalNumber, int stationToOrdinalNumber) {
		List<Station> stationList = new ArrayList<Station>();
		String hql = "SELECT r.stationId FROM Route r "
					+ "WHERE r.trainId=:run.trainId "
					+ "AND (r.stationOrdinalNumber BETWEEN :stationFromOrdinalNumber AND :stationToOrdinalNumber) ";
		stationList = HibernateUtil.getCurrentSession().createQuery(hql)
			.setParameter("stationFromOrdinalNumber", stationFromOrdinalNumber)
			.setParameter("stationToOrdinalNumber", stationToOrdinalNumber)
			.list();
		return stationList;
	}

	public Route findRouteByStationStringAndTrainId(String station, int trainId) {
		String hql = "SELECT r FROM Route r "
					+ "WHERE r.stationId.name=:station "
					+ "AND r.trainId.id=:trainId";
		Route route = (Route) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("station", station)
				.setParameter("trainId", trainId)
				.uniqueResult();
		return route;
	}
	
	
	
}
