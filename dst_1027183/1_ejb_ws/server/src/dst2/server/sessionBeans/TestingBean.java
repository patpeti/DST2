package dst2.server.sessionBeans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst2.server.interfaces.TestingBeanInterface;
import dst2.server.model.Address;
import dst2.server.model.Admin;
import dst2.server.model.Cluster;
import dst2.server.model.Computer;
import dst2.server.model.Environment;
import dst2.server.model.Execution;
import dst2.server.model.Grid;
import dst2.server.model.Job;
import dst2.server.model.Membership;
import dst2.server.model.User;
import dst2.server.model.enums.JobStatus;




@Stateless(mappedName = "TestingBean")
@Remote(TestingBeanInterface.class)
public class TestingBean implements TestingBeanInterface {

	private static final Logger LOG = Logger.getLogger(TestingBean.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 2034672113880690067L;
	/**
	 * 
//	 */
	@PersistenceContext(name="grid")
	private EntityManager em;

	public TestingBean() {
		System.out.println("################################################ TESTING BEAN INITIALIZED #######################################################");
	}
	
	public void fillTestData(){
		//check whether DB is empty
		Query q = em.createQuery("select u from User u");
		List<User> users = (List<User>) q.getResultList();
		if(users == null || users.isEmpty()){
			
			
		//two grids
		Grid g1 = new Grid();
		g1.setCostsPerCPUMinute(new BigInteger("1"));
		g1.setLocation("AUT-VIE");
		g1.setName("ViennaGrid1");
		
		Grid g2 = new Grid();
		g2.setCostsPerCPUMinute(new BigInteger("3"));
		g2.setLocation("AUT-VIE");
		g2.setName("ViennaGrid2");
		
		LOG.info("Grids created");
		//two users
		
		User u1 = new User();
		u1.setAccountNo("0000");
		
		Address a = new Address();
		a.setCity("Vienna");
		a.setStreet("Lorenz Müller");
		a.setZipCode("1200");
		
		u1.setAddress(a);
		u1.setBankCode("1111");
		u1.setFirstName("Peter");
		u1.setLastName("Patonai");
		u1.setUsername("patonaipeter");
		
		User u2 = new User();
		u2.setAccountNo("0001");
		
		Address a2 = new Address();
		a2.setCity("Vienna");
		a2.setStreet("Maria HilferStrasse");
		a2.setZipCode("1010");
		
		u2.setAddress(a2);
		u2.setBankCode("2222");
		u2.setFirstName("Agnes");
		u2.setLastName("Patonai");
		u2.setUsername("patonaiagnes");
		LOG.info("Users created");
		//two membership
		
		Membership m1 = new Membership();
		m1.setDiscount(new Double(10));
		m1.setGrid(g1);
		m1.setUser(u1);
		m1.setRegistration(new Date(new Date().getTime()-100000));
		
		Membership m2 = new Membership();
		m2.setDiscount(20);
		m2.setGrid(g2);
		m2.setUser(u2);
		m2.setRegistration(new Date(new Date().getTime()-100000));
		LOG.info("Memberships created");
		//two cluster
		Admin admin = new Admin();
		admin.setAddress(a);
		admin.setFirstName("admin");
		admin.setLastName("admin");
		
		Cluster cl1 = new Cluster();
		cl1.setAdmin(admin);
		cl1.setGrid(g1);
		cl1.setLastService(new Date(new Date().getTime()-100));
		cl1.setName("WienCluster1");
		cl1.setNextService(new Date());
		
		Cluster cl2 = new Cluster();
		cl2.setAdmin(admin);
		cl2.setGrid(g2);
		cl2.setLastService(new Date(new Date().getTime()-100));
		cl2.setName("WienCluster2");
		cl2.setNextService(new Date());
		
		List<Cluster> clList = new ArrayList<Cluster>();
		clList.add(cl1);
		clList.add(cl2);
		admin.setClusters(clList);
		LOG.info("Clusters created");
		//five computer
		
		Computer c1 = new Computer();
		c1.setCpus(4);
		c1.setCreation(new Date(new Date().getTime()-100000));
		c1.setLastUpdate(new Date(new Date().getTime()-100000));
		c1.setLocation("AUT-VIE@1200");
		c1.setName("computer1");
		
		em.persist(c1);
		
		Computer c2 = new Computer();
		c2.setCpus(16);
		c2.setCreation(new Date(new Date().getTime()-100000));
		c2.setLastUpdate(new Date(new Date().getTime()-100000));
		c2.setLocation("AUT-VIE@1200");
		c2.setName("computer2");
		
		em.persist(c2);
		
		Computer c3 = new Computer();
		c3.setCpus(32);
		c3.setCreation(new Date(new Date().getTime()-100000));
		c3.setLastUpdate(new Date(new Date().getTime()-100000));
		c3.setLocation("GBN-LON@1200");
		c3.setName("computer3");
		
		em.persist(c3);
		
		Computer c4 = new Computer();
		c4.setCpus(32);
		c4.setCreation(new Date(new Date().getTime()-100000));
		c4.setLastUpdate(new Date(new Date().getTime()-100000));
		c4.setLocation("GBN-LON@1200");
		c4.setName("computer4");
		
		em.persist(c4);
		
		Computer c5 = new Computer();
		c5.setCpus(32);
		c5.setCreation(new Date(new Date().getTime()-100000));
		c5.setLastUpdate(new Date(new Date().getTime()-100000));
		c5.setLocation("HUN-BUD@1200");
		c5.setName("computer5");
		
		em.persist(c5);
		
		
		List<Computer> compList1 = new ArrayList<Computer>();
		compList1.add(c1);
		compList1.add(c2);
		
		List<Computer> compList2 = new ArrayList<Computer>();
		compList2.add(c3);
		compList2.add(c4);
		compList2.add(c5);
		
		cl1.setComputers(compList1);
		cl2.setComputers(compList2);
		
		
		LOG.info("Computers created");
		//one job -> execution started 30 min before not finished
		
		Environment e1 = new Environment();
		List<String> params = new ArrayList<String>();
		params.add("output-mode: db");
		params.add("justworkplease");
		e1.setWorkflow("workflow1");
		em.persist(e1);
		Job j1 = new Job();
		j1.setEnvironment(e1);
		j1.setPaid(true);
		j1.setUser(u1);
		em.persist(j1);
		
		Execution ex1 = new Execution();
		ex1.setComputers(compList1);
		ex1.setEnd(null);
		ex1.setJob(j1);
		long dateoffset = 30*60*1000; //30 min in millisec
		ex1.setStart(new Date(new Date().getTime()-dateoffset));
		ex1.setStatus(JobStatus.RUNNING);
		em.persist(ex1);
		
		LOG.info("Jobs and Execustion created");
		
		em.persist(g1);
		em.persist(g2);
		em.persist(u1);
		em.persist(u2);
		em.persist(m1);
		em.persist(m2);
		em.persist(admin);
		em.persist(cl1);
		em.persist(cl2);
		
		LOG.info("*******FINISHED*********");
		
		}
	}
	
	public void setEm(EntityManager em){
		this.em = em;
	}

}
