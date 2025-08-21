package study.data_jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.data_jpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void  save(){

        Item item = new Item("A"); // id가 null이면 무조건 INSERT id가 null이 아니면 해당 id로 SELECT → 있으면 UPDATE, 없으면 INSERT
        itemRepository.save(item);

    }

}