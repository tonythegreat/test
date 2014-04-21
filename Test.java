package functionTest;



import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;


public class Test {
	
	//遍历的Statement个数
	public static int COUNTER = 200; 
	
	public static void main(String args[])  
	    {  
	     	//TDB所处的目录  
	     	String directory = "c:/data/content_tdb/";
	        
	     	//建立以TDB为基础的Dataset
	     	Dataset dataset = TDBFactory.createDataset(directory); 
	        
	     	//建立以Dataset为基础的RDF模型
	     	Model tdb = dataset.getDefaultModel();      	     	
	        
	     	int i = 0;
	     	
	     	//得到遍历TDB中所有statement的迭代器
	        StmtIterator iterator = tdb.listStatements();
	        
	        //通过迭代器返回前COUNTER个statement
	        while(iterator.hasNext() && i<COUNTER){
	        	Statement statement  = iterator.next();
	        	
	        	/*分别得到statement的subject(类型为Resource)
	        	 * predicate(类型为Property)
	        	 * object(类型为RDFNode)
	        	 * 其中subject可能是uri，也可能是空白节点
	        	 * predicate只可能是uri；object可能是uri、空白节点或者literal
	        	 */
	        	Resource subject = statement.getSubject();
	        	if(subject.isURIResource()){
	        		System.out.print(subject.getURI() + " ");
	        	}else{
	        		System.out.print(subject.getId() + " ");
	        	}
	        	
	        	
	        	Property predicate  = statement.getPredicate();
	        	System.out.print(predicate.getURI() + " ");
	        	
	        	RDFNode object  = statement.getObject();
	        	if(object.isURIResource()){
	        		System.out.println(object.asResource().getURI());
	        	}else if(object.isAnon()){
	        		System.out.print(object.asResource().getId());
	        	}else if(object.isLiteral()){
	        		System.out.println(object.asLiteral());
	        	}else{
	        		System.out.println("haha");
	        	}
	        }
	        
	        //使用结束后分别关闭tdb和dataset，该步骤切记不能省略，否则tdb会被破坏
	        tdb.close();
	        dataset.close();       
	    }
}

