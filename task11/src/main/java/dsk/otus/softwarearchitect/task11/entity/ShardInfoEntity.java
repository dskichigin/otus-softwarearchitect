package dsk.otus.softwarearchitect.task11.entity;

public class ShardInfoEntity {
    private Integer shard = 0;
    private Long countRow = 0L;

    public ShardInfoEntity(Integer shard) {
        this.shard = shard;
    }
    public ShardInfoEntity(Integer shard, Long countRow) {
        this.shard = shard;
        this.countRow = countRow;
    }
    public Integer getShard() {
        return shard;
    }

    public void setShard(Integer shard) {
        this.shard = shard;
    }

    public Long getCountRow() {
        return countRow;
    }

    public void setCountRow(Long countRow) {
        this.countRow = countRow;
    }
}
