package bgoug;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import oracle.kv.Consistency;
import oracle.kv.Depth;
import oracle.kv.Direction;
import oracle.kv.Durability;
import oracle.kv.DurabilityException;
import oracle.kv.FaultException;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.KeyRange;
import oracle.kv.KeyValueVersion;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.RequestTimeoutException;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.kv.avro.AvroCatalog;
import oracle.kv.avro.GenericAvroBinding;
import oracle.kv.avro.JsonAvroBinding;
import oracle.kv.avro.JsonRecord;
import oracle.kv.avro.SpecificAvroBinding;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class Example {

  private KVStore store;
  private KVStoreConfig config;

  public void getHandle() {
    String[] hosts = {"localhost:5000", "127.0.0.1:5000"};
    config = new KVStoreConfig("example", hosts);
    // set default time out
    config.setRequestTimeout(50, TimeUnit.MILLISECONDS);
    // can set consistency, durability
    //config.setConsistency(Consistency.ABSOLUTE)
    //      .setDurability(Durability.COMMIT_SYNC);
    // set*Void()
    store = KVStoreFactory.getStore(config);
  }

  public void release() {
    store.close();
  }

  public void getHandleJava7() {
    try (KVStore store1 = KVStoreFactory.getStore(config)) {
      // MEAT GOES HERE
    } catch (Exception e) {
    } finally {
    }
  }

  public void writeKeyValue() throws UnsupportedEncodingException {
    
    List<String> major = new ArrayList<>();
    major.add("Muffin");
    major.add("Man");
    
    List<String> minor = new ArrayList<>();
    minor.add("address");
    
    Key k = Key.createKey(major, minor);
    String address = "Drury Lane";
    Value v = Value.createValue(address.getBytes("UTF-8"));
    store.put(k, v);
    store.putIfAbsent(k, v);
    store.putIfPresent(k, v);
    store.putIfVersion(k, v, null);
  }
  
  public void deleteKeyValue(){
    List<String> major = Arrays.asList("Muffin", "Man");
    List<String> minor = Arrays.asList("address");
    Key k = Key.createKey(major, minor);
    store.delete(k);
    store.multiDelete(Key.createKey(major), null, null);
  }
  
  public void readARecord() throws UnsupportedEncodingException{
    List<String> major = Arrays.asList("Muffin", "Man");
    List<String> minor = Arrays.asList("address");
    Key k = Key.createKey(major, minor);
    ValueVersion vv = store.get(k);
    Value v = vv.getValue();
    String result = new String(v.getValue(), "UTF-8");
    result.equals("Drury Lane");
  }
  
  public void readFullMajor1Go(){
    List<String> major = Arrays.asList("Muffin", "Man");
    Key k = Key.createKey(major);
    // Single operation
    SortedMap<Key,ValueVersion> records = store.multiGet(k, null, null);
    for (Map.Entry<Key, ValueVersion> entry : records.entrySet()) {
      Key key = entry.getKey();
      List<String> minor = key.getMinorPath();
      ValueVersion vv = entry.getValue();
      Value v = vv.getValue();
      // Do some work with the Value here
    }
  }
  
  public void readFullMajorManyGoes(){
    List<String> major = Arrays.asList("Muffin", "Man");
    Key k = Key.createKey(major);
    // Non atomic, when result is too big
    Iterator<KeyValueVersion> it =
            store.multiGetIterator(
            Direction.FORWARD, // BACKWARD, UNORDEREDED
            0, // Batch size, 0 - use default
            k, // the key
            null, // KeyRange
            null); // Depth - CHILDREN_ONLY, PARENT_AND_CHILDREN,
                   // DESCENDANTS_ONLY, PARENT_AND_DESCENDANTS 
    while (it.hasNext()){
      Value v = it.next().getValue();
      // Do some work with the Value here
    }
   
  }
  
  public void readPartialMatch() {
    List<String> major = Arrays.asList("Muffin");
    Key k = Key.createKey(major);
    // Non atomic, when result is too big
    Iterator<KeyValueVersion> it =
            store.storeIterator(
            Direction.UNORDERED, // BACKWARD, FORWARD
            0, // Batch size, 0 - use default
            k, // the key
            null, // KeyRange
            null); // Depth - CHILDREN_ONLY, PARENT_AND_CHILDREN,
                   // DESCENDANTS_ONLY, PARENT_AND_DESCENDANTS 
    while (it.hasNext()){
      Value v = it.next().getValue();
      // Do some work with the Value here
    }
  }
  
    public void prepareKeyRange() {
      // Bowerick Wowbagger the Infinitely Prolonged
      // Hitchhikers Guide To the Galaxy
      // Arthur Philip Dent - You are a jerk 
      KeyRange kr = new KeyRange(
              "Arthur Philip Dent", // start
              true, // inclusive? [(
              "A-Rth-Urp-Hil-Ipdenu", // slug
              true); // inclusive? )]
  }
    
    public void sequence(){
      OperationFactory of = store.getOperationFactory();
      List<Operation> ops = new ArrayList<>();
      Key k = null; Value v = null;
      ops.add(of.createDelete(k));
      ops.add(of.createPut(k, v));
      // of.createDeleteIfVersion();
      // of.createPutIfAbsent();
      // of.createPutIfPresent();
      // of.createPutIfVersion()
      try { store.execute(ops);
      } catch (OperationExecutionException | // sequence cannot be executed
              DurabilityException | // durability cannot be met
              IllegalArgumentException | // OpList is empty or null
              RequestTimeoutException e) { // timeout
      } catch (FaultException e) { // Sth else, superclass of durability
        // sth else
      }
    }
    
  public void genericAvro() throws IOException{
    Map<String, Schema> schemas = new HashMap<>();
    Schema.Parser parser = new Schema.Parser();
    Schema developerSchema = parser.parse(new File("DeveloperSchema.avsc"));
    schemas.put(developerSchema.getFullName(), developerSchema);
    Schema dbAdminSchema = parser.parse(new File("DbAdminSchema.avsc"));
    schemas.put(dbAdminSchema.getFullName(), dbAdminSchema);
    
    AvroCatalog catalog = store.getAvroCatalog();
    GenericAvroBinding binding = catalog.getGenericMultiBinding(schemas);
    
    GenericRecord dev = new GenericData.Record(developerSchema);
    dev.put("name", "Sam A. Hacker");
    dev.put("age", 37);
    dev.put("language", "Java");
    Key k = null;  //Key.createKey
    store.put(k, binding.toValue(dev));
    
    Value v = store.get(k).getValue();
    GenericRecord dbAdmin = binding.toObject(v);
    dbAdmin.get("...");
  }
  
  public void specificAvro(){
    AvroCatalog catalog = store.getAvroCatalog();
    SpecificAvroBinding binding = catalog.getSpecificMultiBinding();
    // generate via ant task
    // org.apache.avro.compiler.specific.SchemaTask
    Developer dev = new Developer();
    dev.setName("Sam. A. Hacker");
    dev.setAge(37);
    dev.setLanguage("Java");
    
    Key k = null;  //Key.createKey
    store.put(k, binding.toValue(dev));
    
    Value v = store.get(k).getValue();
    SpecificRecord sr = binding.toObject(v);
    if (sr.getSchema().getFullName().equals("dba")){
      DbAdmin dbAdmin = (DbAdmin) sr;
    }
    
  }
  
  public void jsonAvro() throws IOException{
    AvroCatalog catalog = store.getAvroCatalog();
    Map<String, Schema> schemas = new HashMap<>();
    Schema.Parser parser = new Schema.Parser();
    Schema developerSchema = parser.parse(new File("DeveloperSchema.avsc"));
    schemas.put(developerSchema.getFullName(), developerSchema);
    Schema dbAdminSchema = parser.parse(new File("DbAdminSchema.avsc"));
    schemas.put(dbAdminSchema.getFullName(), dbAdminSchema);
    JsonAvroBinding binding = catalog.getJsonMultiBinding(schemas);

    String jsonText = 
            "{\"name\": \"Sam. A. Hacker\", \"age\": 34, \"language\": \"Java\"}";
     ObjectMapper jsonMapper = new ObjectMapper();
    JsonNode json = jsonMapper.readTree(jsonText);
    JsonRecord dev = new JsonRecord(json, developerSchema);
    Key k = null;  //Key.createKey
    store.put(k, binding.toValue(dev));
    
    Value v = store.get(k).getValue();
    JsonRecord jr = binding.toObject(v);
    if (jr.getSchema().getFullName().equals("dba")){
      JsonNode dbAdmin =jr.getJsonNode();
      dbAdmin.get("db");
    }

  }
  public void lobOps() {
  }
 

}
