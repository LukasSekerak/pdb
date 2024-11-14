package sk.upjs.gursky.pdb;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import sk.upjs.gursky.bplustree.BPTree;

public class ClusteredBPTree extends BPTree<PersonStringKey, PersonEntry> {
	

	public static final File INDEX_FILE = new File("person.tree"); 
	public static final File INPUT_DATA_FILE = new File("person.tab"); 

	private ClusteredBPTree() {
		super(PersonEntry.class, INDEX_FILE);
	}
	
	public static ClusteredBPTree createOneByOne() throws IOException {
		long startTime = System.nanoTime();
		ClusteredBPTree tree = new ClusteredBPTree();
		RandomAccessFile raf = new RandomAccessFile(INPUT_DATA_FILE, "r");
		
		FileChannel channel = raf.getChannel();
		ByteBuffer  buffer = ByteBuffer.allocateDirect(4096);
		
		long fileSize = INPUT_DATA_FILE.length();
		for (int offset = 0; offset < fileSize; offset += 4096) {
			System.out.println("Processing page " + offset / 4096);
			buffer.clear();
			channel.read(buffer, offset);
			buffer.rewind();
			int numberOfRecords = buffer.getInt();
			
			for (int i = 0; i < numberOfRecords; i++) {
				PersonEntry entry = new PersonEntry();
				entry.load(buffer);
				tree.add(entry);
			}
			
		}
		
		channel.close();
		raf.close();
		System.out.println("Index created in " + (System.nanoTime() - startTime)/1000000 + " ms" );
		return tree;
	}
	
	public static ClusteredBPTree createByBulkLoading() throws IOException {
		long startTime = System.nanoTime();
		ClusteredBPTree tree = new ClusteredBPTree();
		RandomAccessFile raf = new RandomAccessFile(INPUT_DATA_FILE, "r");
		
		FileChannel channel = raf.getChannel();
		ByteBuffer  buffer = ByteBuffer.allocateDirect(4096);
		List<PersonEntry> entries = new ArrayList<>();
		
		long fileSize = INPUT_DATA_FILE.length();
		for (int offset = 0; offset < fileSize; offset += 4096) {
			System.out.println("Processing page " + offset / 4096);
			buffer.clear();
			channel.read(buffer, offset);
			buffer.rewind();
			int numberOfRecords = buffer.getInt();
			
			for (int i = 0; i < numberOfRecords; i++) {
				PersonEntry entry = new PersonEntry();
				entry.load(buffer);
				entries.add(entry);
			}
			
		}
		Collections.sort(entries);
		tree.openAndBatchUpdate(entries.iterator(), entries.size());
		channel.close();
		raf.close();
		System.out.println("Index created in " + (System.nanoTime() - startTime)/1000000 + " ms" );
		return tree;		
	}

}
