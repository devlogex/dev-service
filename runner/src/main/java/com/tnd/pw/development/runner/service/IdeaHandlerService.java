package com.tnd.pw.development.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.IdeaRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;

public interface IdeaHandlerService {
    CsDevRepresentation addIdea(DevRequest request) throws DBServiceException;

    CsDevRepresentation updateIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException;

    CsDevRepresentation getIdea(DevRequest request) throws DBServiceException;

    IdeaRep getIdeaInfo(DevRequest request) throws DBServiceException, IdeaNotFoundException, ActionServiceFailedException;

    IdeaRep voteIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException, ActionServiceFailedException;
}
