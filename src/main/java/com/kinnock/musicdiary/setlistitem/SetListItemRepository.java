package com.kinnock.musicdiary.setlistitem;

import com.kinnock.musicdiary.setlistitem.entity.SetListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetListItemRepository extends JpaRepository<SetListItem, Long> {

}
