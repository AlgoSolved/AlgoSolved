import { setupWorker, SetupWorkerApi } from 'msw/browser';
import { handlers } from './handlers';

export const worker: SetupWorkerApi = setupWorker(...handlers);
