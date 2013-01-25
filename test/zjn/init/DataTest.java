package zjn.init;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import zjn.dao.MovieDao;
import zjn.dao.PrefileDao;
import zjn.dao.RatingDao;
import zjn.dao.UserDao;
import zjn.model.Movie;
import zjn.model.Prefile;
import zjn.model.Rating;
import zjn.model.User;
import zjn.util.CountScore;
import zjn.util.RandomData;


public class DataTest {
	
  private static String [] username = {"����Ӣ","������","�캣Ӣ","�콭�","����","�޳�Ⱥ","κ����",
	                                    "֣����","����","�ٺ�","̫��","��Ȱ��","����","����","ͦ��",
	                                    "������","�ܶ���","���ٺ�","л̫��","��Ȱ��","���Һ�","������","��ͦ��",
	                                    "��Ӣ","����","��Ӣ","���","���","��Ⱥ","κ��"
                                       };
  private static String [] sex = {"m","w"};
  
  private static String [] type = {"�ֲ�","����","ϲ��","����","���","ð��","ս��",
	                               "����","����","�ƻ�","����","��ͥ","��¼","����","����",
                                     };
  private static String [] movieName = {"��ҹ��","�����","��ʱ׷��","����ս��","������","�߳�һ��δ��","��С����","����������","̸̸��������","�Ȱ���",
	                                    "������","����ʱ��","ʳ����","��ľ��","��Ⱦ��","�޹���","���ݸ氲ȫ","���ݸ�","�氲ȫ","��������",
	                                    "��ɫ����","����","ɫ��","�������","�����","����","���","�������","���","����",
	                                    "����","����","����","����","�۰�����","����","�۰�","��֮�۰�","","",
                                   "��ҹ","����","��ʱ","����ս��2","����","δ��","С����","������","̸����","�ȵ�",
                                   "��Ȫ����","����","����","��ӭ","����","ģ��","ǰ��","����","ʢ��","���۾ź�",
                                   "�ɴ�","���ڴ�","���","���״�","ͨ��","�˹���","���Խ�","�ڹ�����","�칬һ��","Ŀ�������"};
  @Test
  public void testAddUser(){
	  User user ;
	  List<User> userList= new ArrayList<User>();
	  List<String> hobbyList; 
	  int[] b = RandomData.randomData();
	  int [] a = RandomData.randomDataList();
	  for(int i=1;i<16;i++){
		  hobbyList = new ArrayList<String>();
		  user = new User();
		  user.setId(i+15);
		  user.setName(username[i+14]);
		  String s1 =  type[a[i-1]];
		  String s2 = type[a[i+14]];
		  String s3 = type[a[i+29]];
		  System.out.println(s1);
		  System.out.println(s2);
		  System.out.println(s3);
		  System.out.println("--------");
		  hobbyList.add(s1);
		  hobbyList.add(s2);
		  hobbyList.add(s3);
		 
		  user.setSex(sex[b[i-1]]);
		  user.setHobbyList(hobbyList);
		  userList.add(user);
		  //hobbyList.clear();
	  }
	  
	  UserDao.insertUsers(userList);
	  
  }
  
  @Test
 public void testAddMovies(){
	  
	  Movie movie;
	  List<Movie> movieList= new ArrayList<Movie>();
	  List<String> TypeList;
	  int [] a = RandomData.randomDataList2();
	  for(int i = 1;i<movieName.length+1;i++){
		  TypeList = new ArrayList<String>();
		  movie = new Movie();
		  movie.setId(i);
		  movie.setName(movieName[i-1]);
		  movie.setYear("1989-7-1");
		  String s1 = type[a[i-1]];
		  String s2 = type[a[i+49]];
		  String s3 = type[a[i+99]];
		  TypeList.add(s1);
		  TypeList.add(s2);
		  TypeList.add(s3);
		  movie.setType(TypeList);
		  movieList.add(movie);  
	  }
	  
	  MovieDao.insertMovies(movieList);
 }
  
  @Test
  public void testAddMovies2(){
 	  
 	  Movie movie;
 	  List<Movie> movieList= new ArrayList<Movie>();
 	  List<String> TypeList;
 	  int [] a = RandomData.randomDataList3();
 	  for(int i = 1;i<21;i++){
 		  TypeList = new ArrayList<String>();
 		  movie = new Movie();
 		  movie.setId(i+50);
 		  movie.setName(movieName[i+49]);
 		  movie.setYear("1989-7-1");
 		  String s1 = type[a[i-1]];
 		  String s2 = type[a[i+19]];
 		  String s3 = type[a[i+39]];
 		  TypeList.add(s1);
 		  TypeList.add(s2);
 		  TypeList.add(s3);
 		  movie.setType(TypeList);
 		  movieList.add(movie);  
 	  }
 	  
 	  MovieDao.insertMovies(movieList);
  }
  
  @Test
  public void testAddRating(){
	  List<Movie> movieList = MovieDao.getAllMovies();
	  List<User> userList = UserDao.getAllUser();
	  List<Rating> rateList=CountScore.count(userList, movieList);
	  RatingDao.insertRatings(rateList);
  }
  
  @Test
  public void testAddRating2(){
	  
	  List<User> userList = UserDao.getAllUser();
	  List<Rating> rateList = new ArrayList<Rating>();
	  System.out.println(userList.size());
	  for(int i=0;i<30;i++){
		 List<Movie> movieList = MovieDao.getMovies(RandomData.randomDataList4());
		 rateList=CountScore.count2(userList.get(i), movieList);
	  }
	 
	  RatingDao.insertRatings(rateList);
  }
  
  @Test
  public void testAddRating3(){
	  List<User> userList = UserDao.getAllUser();
	  System.out.println(userList.size());
	  List<Rating> rateList=CountScore.count3(userList);
	  //RatingDao.insertRatings(rateList);
  }
  
  @Test
  public void testAddPrifile(){
	  List<Movie> movieList = MovieDao.getMovies(RandomData.randomDataList4());
	  List<User> userList = UserDao.getAllUser();
	  List<Prefile> prefiles = CountScore.countPrefile(userList, movieList);
	  for(Prefile p:prefiles){
		  System.out.println(p.getSex());
	  }
	  PrefileDao.insertPrefile(prefiles);
  }
  


}
