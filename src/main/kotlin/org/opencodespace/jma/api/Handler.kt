package org.opencodespace.jma.api

import org.opencodespace.jma.api.entity.Notification
import org.opencodespace.jma.api.entity.Status


interface Handler {

    fun onStatus(status: Status)

    //ignore if public streaming
    fun onNotification(notification: Notification)

    //ignore if public streaming
    fun onDelete(id: Long)
}