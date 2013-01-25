package zjn.recommender;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

import zjn.model.Prefile;
public class GenderRescorer implements IDRescorer {
	
	  private final FastIDSet men;//��ŵ�ǰ����ģ�Ͷ�Ӧ������male selectableUser
	  private final FastIDSet women;//��ŵ�ǰ����ģ�Ͷ�Ӧ������female selectableUser
	  private final FastIDSet usersRateMoreMen;
	  private final FastIDSet usersRateLessMen;
	  private final boolean filterMen;//�������һ���û���userID���壩һ��profileID�Ƿ�Ӧ�ù���

	  public GenderRescorer(FastIDSet men,
	                        FastIDSet women,
	                        FastIDSet usersRateMoreMen,
	                        FastIDSet usersRateLessMen,
	                        long userID, DataModel model)
	      throws TasteException {
	    this.men = men;
	    this.women = women;
	    this.usersRateMoreMen = usersRateMoreMen;
	    this.usersRateLessMen = usersRateLessMen;
	    this.filterMen = ratesMoreMen(userID, model);
	  }
	 
	//�������ݶ�Ӧ��men��women����(�����ж��Ĳ���Ӱ�������Ի���Ů��ϲ����
	public static FastIDSet[] parseMenWomen(List<Prefile> prefiles){
		FastIDSet men = new FastIDSet(1000);
	    FastIDSet women = new FastIDSet(1000);
	    for(Prefile p:prefiles){
	    	if("u".equals(p.getSex())){
	    		continue;
	    	}
	    	
	    	if("m".equals(p.getSex())){
	    		men.add(p.getMovieID());
	    	}else{
	    		women.add(p.getMovieID());
	    	}
	    }
	    
	    men.rehash();
	    women.rehash();
	    return new FastIDSet[] { men, women };
	}
    
	 //�ж�userID��Ӧ���û��ǲ��Ǹ�ϲ�����ԣ�����/�������ֵ���Щ�û����Ա���ͳ��
	 //������ʷ������ͳ�ƣ���movie_preferences���н���ͳ�ƣ��ó����еĸ�ϲ���õ�Ӱ����Ů��
	 //PreferenceArray��洢����ĳһ���ã�����ϲ������Ʒ�����п����ƶϳ����û��Ǹ��ӵ�Ů�Ի��������Ի�
	private boolean ratesMoreMen(long userID, DataModel model) {
		 if (usersRateMoreMen.contains(userID)) {
		      return true;
		    }
		    if (usersRateLessMen.contains(userID)) {
		      return false;
		    }
		    PreferenceArray prefs;
			try {
				prefs = model.getPreferencesFromUser(userID);
				int menCount = 0;
			    int womenCount = 0;
			    for (int i = 0; i < prefs.length(); i++) {
			      long profileID = prefs.get(i).getItemID();
			      if (men.contains(profileID)) {
			        menCount++;
			      } else if (women.contains(profileID)) {
			        womenCount++;
			      }
			    }
			    
			    boolean ratesMoreMen = menCount > womenCount;
			    if (ratesMoreMen) {
			      usersRateMoreMen.add(userID);
			    } else {
			      usersRateLessMen.add(userID);
			    }
			    return ratesMoreMen;
			} catch (TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return false;
		  
	}

	//������Ҫ���˵��Ƽ���������ֵΪNaN��������Ϊ���ǲ��ǲ����Ƽ��ģ����������Ƽ�
	  @Override
	  public double rescore(long profileID, double originalScore) {
	    return isFiltered(profileID) ? Double.NaN : originalScore;
	  }

	//���һ���û���ϲ�����Եģ����Ƽ�������Ů�ԣ�������Ƽ���Ӧ�ù��˵��ģ���֮��Ȼ
	  @Override
	  public boolean isFiltered(long profileID) {
	    return filterMen ? men.contains(profileID) : women.contains(profileID);
	  }

}
