package org.springframework.uac.controller.review;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.utils.LogEventType;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.uac.controller.BaseController;
import org.springframework.uac.model.domain.GzReview;
import org.springframework.uac.model.dto.review.ReviewDto;
import org.springframework.uac.service.review.GzReviewService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sir.D
 * @since 2020-08-03
 */
@RestController
@IRequestMapping(value = "/gz-review", method = RequestMethod.POST, id = 400, parentIds = 3, roleType = 3, component = "PendingExamine")
public class GzReviewController extends BaseController {

    @Autowired
    private GzReviewService service;

    /**
     * 用户列表
     * @param user
     * @return
     */
    @Log(eventType = LogEventType.FIND, message = "【查询审核列表】操作")
    @IRequestMapping(value = "/list", id = 401)
    public Message list(@RequestBody GzReview user) {
        PageInfo<GzReview> page = this.service.findAll(user);
        return this.improve(new Message(page));
    }


    /**
     * 审核批准
     * @return
     */
    @Log(eventType = LogEventType.APPROVE, level = 1, message = "【审核批准】操作，批准了审核列表中ID为【`{0}`】的请求", params = {"reviewId"})
    @IRequestMapping(value = "/approve", id = 402)
    public Message approve(@Valid @RequestBody ReviewDto review) throws SpringSafeException {
        this.service.approve(review.getReviewId());
        return this.improve(new Message(Status.SUCCESS));

    }

    /**
     * 审核拒绝
     * @return
     */
    @Log(eventType = LogEventType.REFUSE, level = 1, message = "【审核拒绝】操作，拒绝了审核列表中ID为【`{0}`】的请求", params = {"reviewId"})
    @IRequestMapping(value = "/refuse", id = 403)
    public Message refuse(@Valid @RequestBody ReviewDto review) throws SpringSafeException {
        this.service.refuse(review.getReviewId());
        return this.improve(new Message(Status.SUCCESS));
    }

}
