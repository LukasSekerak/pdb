package sk.upjs.gursky.pdb;

import sk.upjs.gursky.bplustree.BPKey;

import java.nio.ByteBuffer;

public class SalaryKey implements BPKey<SalaryKey> {

    private int key;

    public SalaryKey() {}

    public SalaryKey(int key) {
        this.key = key;
    }
    @Override
    public void load(ByteBuffer bb) {
        key = bb.getInt();
    }

    @Override
    public void save(ByteBuffer bb) {
        bb.putInt(key);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public int compareTo(SalaryKey o) {
        return Integer.compare(key, o.key);
    }
}
