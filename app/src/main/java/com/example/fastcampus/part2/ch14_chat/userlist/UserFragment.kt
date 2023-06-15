package com.example.fastcampus.part2.ch14_chat.userlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.DB_CHAT_ROOMS
import com.example.fastcampus.DB_USERS
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentUserlistBinding
import com.example.fastcampus.part2.ch14_chat.chatdetail.ChatDetailActivity
import com.example.fastcampus.part2.ch14_chat.chatdetail.ChatDetailActivity.Companion.CHAT_ROOM_ID
import com.example.fastcampus.part2.ch14_chat.chatdetail.ChatDetailActivity.Companion.OTHER_USER_ID
import com.example.fastcampus.part2.ch14_chat.chatlist.ChatRoomItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class UserFragment : Fragment(R.layout.fragment_userlist) {

    private lateinit var binding: FragmentUserlistBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserlistBinding.bind(view)

        val userAdapter = UserAdapter { otherUser ->
            val myUserId = Firebase.auth.currentUser?.uid ?: ""
            val chatRoomDB = Firebase.database.reference.child(DB_CHAT_ROOMS).child(myUserId)
                .child(otherUser.userId ?: "")

            chatRoomDB.get().addOnSuccessListener {
                var chatRoomId = ""

                if (it.value != null) {
                    val chatRoom = it.getValue(ChatRoomItem::class.java)
                    chatRoomId = chatRoom?.chatRoomId ?: ""
                } else {
                    chatRoomId = UUID.randomUUID().toString()
                    val newChatRoom = ChatRoomItem(
                        chatRoomId = chatRoomId,
                        otherUserName = otherUser.username,
                        otherUserId = otherUser.userId
                    )

                    chatRoomDB.setValue(newChatRoom)
                }

                val intent = Intent(context, ChatDetailActivity::class.java).apply {
                    putExtra(OTHER_USER_ID, otherUser.userId)
                    putExtra(CHAT_ROOM_ID, chatRoomId)
                }
                startActivity(intent)
            }
        }

        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""

        Firebase.database.reference.child(DB_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userItemList = mutableListOf<UserItem>()

                    snapshot.children.forEach {
                        val user = it.getValue(UserItem::class.java) ?: return
                        if (user.userId != currentUserId) {
                            userItemList.add(user)
                        }
                    }

                    userAdapter.submitList(userItemList)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
}