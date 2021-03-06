package org.tron.service.eventactuator.sidechain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.tron.client.MainChainGatewayApi;
import org.tron.common.exception.RpcConnectException;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Sidechain.TaskEnum;
import org.tron.service.check.TransactionExtensionCapsule;
import org.tron.service.eventactuator.Actuator;

@Slf4j(topic = "sideChainTask")
public class WithdrawTRXActuator extends Actuator {

  // "event WithdrawTRX(address from, uint256 value, bytes memory txData);"

  private String from;
  private String value;
  private String txData;

  public WithdrawTRXActuator(String from, String value, String txData) {
    this.from = from;
    this.value = value;
    this.txData = txData;
  }

  @Override
  public TransactionExtensionCapsule createTransactionExtensionCapsule() throws RpcConnectException {
    if (Objects.nonNull(transactionExtensionCapsule)) {
      return this.transactionExtensionCapsule;
    }
    logger.info("WithdrawTRXActuator, from: {}, value: {}, txData: {}", this.from, this.value,
      this.txData);
    Transaction tx = MainChainGatewayApi.withdrawTRXTransaction(this.from, this.value, this.txData);
    this.transactionExtensionCapsule = new TransactionExtensionCapsule(TaskEnum.MAIN_CHAIN, tx);

    return this.transactionExtensionCapsule;
  }

}
